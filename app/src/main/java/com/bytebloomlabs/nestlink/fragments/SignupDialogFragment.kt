package com.bytebloomlabs.nestlink.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.bytebloomlabs.nestlink.models.Backend
import com.bytebloomlabs.nestlink.databinding.FragmentSignupDialogBinding

class SignupDialogFragment : DialogFragment() {

    private var _binding: FragmentSignupDialogBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignupDialogBinding.inflate(inflater, container, false)
        return binding.root
//        return inflater.inflate(R.layout.fragment_auth_dialog, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
    }

    private fun setupButtons() {

        with (binding) {
            btnCloseSignup.setOnClickListener {
                this@SignupDialogFragment.dismiss()
            }

            btnSubmit.setOnClickListener {
                val (username, email, password) = getUserInputs()
                Log.i(TAG, "sign up\n\tuser name: <$username>\n\temail: <$email>\n\tpassword: <$password>")

                Backend.signUp(username, email, password)
            }

            btnConfirmSignUp.setOnClickListener {
                val (username, email, password) = getUserInputs()
                val confirmationCode = binding.tiConfirmationCode.editText?.text.toString()
                Log.i(TAG, "confirm signup for: $username")

                Backend.confirmSignUp(username, confirmationCode)
            }
        }
    }

    private fun getUserInputs(): Triple<String, String, String> {
        val userName = binding.tiUsername.editText?.text.toString()
        val userEmail = binding.tiEmail.editText?.text.toString()
        val userPassword = binding.tiPassword.editText?.text.toString()

        return Triple(userName, userEmail, userPassword)
    }

    companion object {
        const val TAG = "AuthDialogFragment"
    }

}