package com.bytebloomlabs.nestlink

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bytebloomlabs.nestlink.databinding.FragmentDataListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DataListFragment : Fragment() {

    private var _binding: FragmentDataListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDataListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //prepare list view and recyclerview (cells)
        setupRecyclerView(binding.itemList)

        setupAuthButton(UserData)

        UserData.isSignedIn.observe(viewLifecycleOwner, Observer<Boolean> {isSignedUp ->
            // update UI
            Log.i(TAG, "onCreate: isSignedIn changed. isSignedUp: $isSignedUp")

            if (isSignedUp) {
                binding.fabAuth.setImageResource(R.drawable.ic_lock_open)
            } else {
                binding.fabAuth.setImageResource(R.drawable.ic_lock)
            }

        })


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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
                Backend.signOut()
            } else {
                authButton.setImageResource(R.drawable.ic_lock_open)
                Backend.signIn(requireActivity())
            }
        }
    }


    companion object {
        const val TAG = "DataListFragment"
    }

}