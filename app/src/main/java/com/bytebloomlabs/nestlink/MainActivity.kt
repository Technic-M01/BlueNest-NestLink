package com.bytebloomlabs.nestlink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bytebloomlabs.nestlink.databinding.ActivityMainBinding
import com.bytebloomlabs.nestlink.fragments.DataListFragmentDirections
import com.bytebloomlabs.nestlink.fragments.LoginFragmentDirections
import com.bytebloomlabs.nestlink.models.Backend
import com.bytebloomlabs.nestlink.models.SessionViewModel
import com.bytebloomlabs.nestlink.models.UserData
import com.bytebloomlabs.nestlink.utils.showCustomToast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val sessionViewModel: SessionViewModel by viewModels()

    private lateinit var navController: NavController

    private var currentFragLabel: String? = null

    private var previouslySignedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        navController.addOnDestinationChangedListener { controller, destination, _ ->
            Log.i("FragController", "destination: ${destination.label}")

            currentFragLabel = destination.label.toString()

            if (destination.label == getString(R.string.login_frag_label)) {


            }

            when (destination.label) {
                getString(R.string.login_frag_label) -> {
                    val authFrag = supportFragmentManager.findFragmentById(R.id.loginFragment)
//                val t = authFrag.getTest()


                    Log.i("FragController", "in auth frag | frag: $authFrag")

                }

                getString(R.string.data_list_frag_label) -> {
//                    binding.fabAddDataPoint.visibility = View.VISIBLE
                }

            }

            if (destination.label == getString(R.string.add_data_point_frag_label)) {
                binding.fabAuth.visibility = View.INVISIBLE
            } else {
                binding.fabAuth.visibility = View.VISIBLE
            }


            if (destination.label != getString(R.string.data_list_frag_label)) {
                binding.fabAddDataPoint.visibility = View.INVISIBLE
            } else {
                binding.fabAddDataPoint.visibility = View.VISIBLE

            }


            setupAuthButton(UserData)

            setupAppBar()

            observeViewModel()

        }

        UserData.isSignedIn.observe(this, Observer<Boolean> { isSignedUp ->
            // update UI
            Log.i(TAG, "onCreate: isSignedIn changed. isSignedUp: $isSignedUp")

            if (isSignedUp) {
                binding.fabAuth.setImageResource(R.drawable.ic_lock_open)

//                changeFragments(NavDestinations.DataList)
                changeFragments(NavDestinations.Home)

                if (!previouslySignedIn) {
                    previouslySignedIn = true
                }

            } else {
                binding.fabAuth.setImageResource(R.drawable.ic_lock)

                if (previouslySignedIn) {
                    if (currentFragLabel != null && currentFragLabel != getString(R.string.login_frag_label)) {
                        navController.popBackStack()
                    }
                }
            }
        })

    }

    // allows user to handle the 'up' button in the toolbar (requires implementation in activity)
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    private fun setupAuthButton(userData: UserData) {
        binding.fabAuth.setOnClickListener {
            val authButton = it as FloatingActionButton

            if (userData.isSignedIn.value!!) {
                authButton.setImageResource(R.drawable.ic_lock_open)
                Backend.signOut()
            } else {
                authButton.setImageResource(R.drawable.ic_lock_open)
//                Backend.signIn(this)
            }
        }

        binding.fabAddDataPoint.setOnClickListener {
            Log.i(TAG, "fab add data point click")
            changeFragments(NavDestinations.AddDataPoint)
        }

    }

    private fun setupAppBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            var mi = ""
            when (menuItem.itemId) {
                R.id.mi_settings -> {
                    mi = "m1"
                    Log.i(TAG, "app bar menu item click: $mi")
                    true
                }

                R.id.mi_filter -> {
                    mi = "m2"
                    Log.i(TAG, "app bar menu item click: $mi")
                    true
                }

                else -> false
            }
        }
    }

    private fun changeFragments(destination: NavDestinations) {

        val action = when (destination) {
            NavDestinations.DataList -> LoginFragmentDirections.actionLoginFragmentToDataListFragment()
            NavDestinations.AddDataPoint -> DataListFragmentDirections.actionDataListFragmentToAddDataPointFragment()
            NavDestinations.Home -> LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        }

        navController.navigate(action)
    }

    private fun observeViewModel() {
        val owner = this@MainActivity

        with (sessionViewModel) {

            message.observe(owner) { event ->
                event.getContentIfNotHandled()?.let {
                    showCustomToast(it.message, it.icon, it.severity)
                }
            }

        }
    }

    companion object {
        private const val TAG = "NestActivity"

        enum class NavDestinations {
            DataList,
            AddDataPoint,
            Home
        }
    }

}