package com.bytebloomlabs.nestlink.models

import android.app.Activity
import android.content.Context
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.api.graphql.model.ModelMutation
import com.amplifyframework.api.graphql.model.ModelQuery
import com.amplifyframework.auth.AuthChannelEventName
import com.amplifyframework.auth.AuthUserAttributeKey
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.auth.options.AuthSignUpOptions
import com.amplifyframework.core.Amplify
import com.amplifyframework.core.InitializationStatus
import com.amplifyframework.datastore.generated.model.EggData
import com.amplifyframework.hub.HubChannel
import com.amplifyframework.hub.HubEvent

object Backend {

    private const val TAG = "Backend"

    fun initialize(applicationContext: Context) : Backend {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.configure(applicationContext)
            Log.i(TAG, "Initialized Amplify")
        } catch (e: AmplifyException) {
            Log.e(TAG, "Could not initialize Amplify", e)
        }

        Log.i(TAG, "registering hub event")

// listen to auth event
        Amplify.Hub.subscribe(HubChannel.AUTH) { hubEvent: HubEvent<*> ->

            when (hubEvent.name) {
                InitializationStatus.SUCCEEDED.toString() -> {
                    Log.i(TAG, "Amplify successfully initialized")
                }
                InitializationStatus.FAILED.toString() -> {
                    Log.i(TAG, "Amplify initialization failed")
                }
                else -> {
                    when (AuthChannelEventName.valueOf(hubEvent.name)) {
                        AuthChannelEventName.SIGNED_IN -> {
                            updateUserData(true)
                            Log.i(TAG, "HUB : SIGNED_IN")
                        }
                        AuthChannelEventName.SIGNED_OUT -> {
                            updateUserData(false)
                            Log.i(TAG, "HUB : SIGNED_OUT")
                        }
                        else -> Log.i(TAG, """HUB EVENT:${hubEvent.name}""")
                    }
                }
            }
        }

        Log.i(TAG, "retrieving session status")

// is user already authenticated (from a previous execution) ?
        Amplify.Auth.fetchAuthSession(
            { result ->
                Log.i(TAG, "initialize: $result")
                updateUserData(result.isSignedIn)
            },
            {
                Log.i(TAG, "initialize: failure")
            }
        )

/*        Amplify.Auth.fetchAuthSession(
            { result ->
                Log.i(TAG, result.toString())
                val cognitoAuthSession = result as AWSCognitoAuthSession
                // update UI
                this.updateUserData(cognitoAuthSession.isSignedIn)
                when (cognitoAuthSession.identityId.type) {
                    AuthSessionResult.Type.SUCCESS ->  Log.i(TAG, "IdentityId: " + cognitoAuthSession.identityId.value)
                    AuthSessionResult.Type.FAILURE -> Log.i(TAG, "IdentityId not present because: " + cognitoAuthSession.identityId.error.toString())
                }
            },
            { error -> Log.i(TAG, error.toString()) }
        )*/



        return this
    }

    // change internal state and query list of data points
    private fun updateUserData(withSignedInStatus : Boolean) {
        UserData.setSignedIn(withSignedInStatus)

        val dataPoints = UserData.eggDataPoints().value
        val isEmpty = dataPoints?.isEmpty() ?: false

        // query data points when signed in and we do not have data points yet
        if (withSignedInStatus && isEmpty) {
            queryEggData()
        } else {
            UserData.resetEggData()
        }


    }

    fun signOut() {
        Log.i(TAG, "Initiate Signout Sequence")

        Amplify.Auth.signOut { Log.i(TAG, "signOut: signed out") }
    }

    fun signIn(userName: String, userPassword: String, callingActivity: Activity) {
        Log.i(TAG, "Initiate Signin Sequence")

        Amplify.Auth.signIn(userName, userPassword,
            {
                Log.i(TAG, "Sign in successful for $userName")
//                Toast.makeText(callingActivity, "Sign in success", Toast.LENGTH_LONG).show()

            },
            {
                Log.w(TAG, "Sign in failed for $userName - ", it)
            })

    }

    fun signUp(userName: String, userEmail: String, userPassword: String) {

        val options = AuthSignUpOptions.builder()
            .userAttribute(AuthUserAttributeKey.email(), userEmail)
            .build()


        Amplify.Auth.signUp(userName, userPassword, options,
            {
                Log.i(TAG, "signUp: signup succeeded: $it")
            },
            {
                Log.e(TAG, "signUp: sign up failed ", it)
            })
    }

    fun confirmSignUp(userName: String, confirmationCode: String) {
        Amplify.Auth.confirmSignUp(userName, confirmationCode,
            { result ->
                if (result.isSignUpComplete) {
                    Log.i(TAG, "confirmSignUp: sign up succeeded")
                } else {
                    Log.i(TAG, "confirmSignUp: sign up not complete")
                }
            },
            {
                Log.e(TAG, "confirmSignUp: failed to confirm sign up.", it)
            })
    }


    fun queryEggData() {
        Log.i(TAG, "Querying notes")

        Amplify.API.query(
            ModelQuery.list(EggData::class.java),
            { response ->
                Log.i(TAG, "Queried")
                for (eggData in response.data) {
                    Log.i(TAG, eggData.toString())
                    //ToDo: should add all the data points at once instead of one by one (each add triggers a UI refresh)
                    UserData.addEggDataPoint(UserData.EggDataPoints.from(eggData))
                }
            },
            { error ->
                Log.e(TAG, "Query failure: ", error)
            }
        )
    }

    fun createEggDataPoint(dataPoint: UserData.EggDataPoints) {
        Log.i(TAG, "Creating data point")

        Amplify.API.mutate(
            ModelMutation.create(dataPoint.data),
            { response ->
                Log.i(TAG, "Created")
                if (response.hasErrors()) {
                    Log.e(TAG, response.errors.first().message)
                } else {
                    Log.i(TAG, "Created data point with id: ${response.data.id}")
                }
            },
            { error ->
                Log.e(TAG, "Create failed: ", error)
            }
        )
    }

    fun deleteDataPoint(dataPoint: UserData.EggDataPoints?) {
        if (dataPoint == null) return

        Log.i(TAG, "Deleting data point: ${dataPoint.toString()}")

        Amplify.API.mutate(
            ModelMutation.delete(dataPoint.data),
            { response ->
                Log.i(TAG, "deleted")
                if (response.hasErrors()) {
                    Log.e(TAG, response.errors.first().message)
                } else {
                    Log.i(TAG, "Deleted data point: $response")
                }
            },
            { error ->
                Log.e(TAG, "Delete failed: ", error)
            }
        )
    }


}