package com.example.authapplication.ui.reg

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.authapplication.R
import com.example.authapplication.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        togglePasswordVisibility()
    }


    private var isPasswordHidden = true
    // for hiding/showing password
    private fun togglePasswordVisibility() {
        binding.passwordLayout.ivShowPassword.setOnClickListener {
            if (isPasswordHidden) {
                // for saving cursor position while doing transformation below(hiding/showing password)
                val selection = binding.passwordLayout.etPassword.selectionEnd
                binding.passwordLayout.ivShowPassword.setImageResource(R.drawable.ic_eye_unhidden)
                binding.passwordLayout.etPassword.transformationMethod = null
                binding.passwordLayout.etPassword.setSelection(selection)
                isPasswordHidden = false
            } else {
                val selection = binding.passwordLayout.etPassword.selectionEnd
                binding.passwordLayout.ivShowPassword.setImageResource(R.drawable.ic_eye)
                binding.passwordLayout.etPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.passwordLayout.etPassword.setSelection(selection)
                isPasswordHidden = true
            }
        }
    }

}