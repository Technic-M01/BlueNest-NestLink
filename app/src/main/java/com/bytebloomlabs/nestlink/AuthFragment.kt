package com.bytebloomlabs.nestlink

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.bytebloomlabs.nestlink.databinding.FragmentAuthBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    val binding get() = _binding!!

    private val sessionViewModel: SessionViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAuthBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAuthButton(UserData)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setupAuthButton(userData: UserData) {

        binding.btnSignIn.setOnClickListener {
            Log.i(TAG, "sign in btn click. signed in value: ${userData.isSignedIn.value}")

            if (userData.isSignedIn.value!!) {
                Backend.signOut()

            } else {
//                Backend.signIn(requireActivity())

                val username = binding.tiUsername.editText?.text.toString()
                val userpassword = binding.tiPassword.editText?.text.toString()
                Backend.signIn(username, userpassword, requireActivity())
            }

//            sessionViewModel.setTriggerAuth(true)

        }

        binding.btnSignUp.setOnClickListener {
            val username = binding.tiUsername.editText?.text.toString()
            val useremail = binding.tiEmail.editText?.text.toString()
            val userpassword = binding.tiPassword.editText?.text.toString()

            Log.i(TAG, "sign up\n\tuser name: <$username>\n\temail: <$useremail>\n\tpassword: <$userpassword>")

            Backend.signUp(username, useremail, userpassword)
        }

        binding.btnConfirmSignUp.setOnClickListener {
            val username = binding.tiUsername.editText?.text.toString()
            val confirmationcode = binding.tiConfirmationCode.editText?.text.toString()

            Backend.confirmSignUp(username, confirmationcode)
        }

    }



    
    companion object {
        const val TAG = "AuthFragment"
    }
    
}