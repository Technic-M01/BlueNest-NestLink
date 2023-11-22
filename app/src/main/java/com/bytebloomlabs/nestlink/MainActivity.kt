package com.bytebloomlabs.nestlink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bytebloomlabs.nestlink.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val sessionViewModel: SessionViewModel by viewModels()

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        navController.addOnDestinationChangedListener { controller, destination, _ ->
            Log.i("FragController", "destination: ${destination.label}")

            if (destination.label == getString(R.string.auth_frag_label)) {
                val authFrag = supportFragmentManager.findFragmentById(R.id.authFragment)



//                val t = authFrag.getTest()


                Log.i("FragController", "in auth frag | frag: $authFrag")

            }

            setupAuthButton(UserData)

            observeViewModel()

        }
//        val frag = sup
/*        //prepare list view and recyclerview (cells)
        setupRecyclerView(binding.itemList)

        setupAuthButton(UserData)

        UserData.isSignedIn.observe(this, Observer<Boolean> {isSignedUp ->
            // update UI
            Log.i(TAG, "onCreate: isSignedIn changed. isSignedUp: $isSignedUp")

            if (isSignedUp) {
                binding.fabAuth.setImageResource(R.drawable.ic_lock_open)
            } else {
                binding.fabAuth.setImageResource(R.drawable.ic_lock)
            }

        })*/

    }

    // allows user to handle the 'up' button in the toolbar (requires implementation in activity)
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    //rv is the list of cells
    private fun setupRecyclerView(recyclerView: RecyclerView) {
        // update individual cells when the EggData contents are modified
        UserData.eggDataPoints().observe(this, Observer<MutableList<UserData.EggDataPoints>> { dataPoints ->
            Log.d(TAG, "setupRecyclerView: EggData observer received ${dataPoints.size} data points")

            //create a recycler view adapter that manages the individual cells
            recyclerView.adapter = EggDataRecyclerViewAdapter(dataPoints)
        })
    }

    private fun setupAuthButton(userData: UserData) {
        binding.fabAuth.setOnClickListener {
            val authButton = it as FloatingActionButton


            if (userData.isSignedIn.value!!) {
                authButton.setImageResource(R.drawable.ic_lock_open)
//                Backend.signOut()
            } else {
                authButton.setImageResource(R.drawable.ic_lock_open)
//                Backend.signIn(this)
            }
            
        }
    }

    private var doOnce = false

    private fun observeViewModel() {
        val owner = this@MainActivity

        with (sessionViewModel) {
            triggerAuth.observe(owner) { run ->
                if (run) {
                    //ToDo auth stuff

                    val action = AuthFragmentDirections.actionAuthFragmentToDataListFragment3()

                    if (!doOnce) {
                        navController.navigate(action)
                        doOnce = true
                    }

                }

            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}