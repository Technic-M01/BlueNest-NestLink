package com.bytebloomlabs.nestlink.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bytebloomlabs.nestlink.R
import com.bytebloomlabs.nestlink.databinding.FragmentDataListBinding
import com.bytebloomlabs.nestlink.databinding.FragmentSettingsBinding
import com.bytebloomlabs.nestlink.models.Backend
import com.bytebloomlabs.nestlink.utils.changeFragments
import com.bytebloomlabs.nestlink.utils.showAddDataPointDialog

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignOut.setOnClickListener {
            Backend.signOut()
        }

        binding.btnAddDataPoint.setOnClickListener {
            requireActivity().showAddDataPointDialog()
        }
    }

    companion object {
        const val TAG = "SettingsFragment"
    }
}