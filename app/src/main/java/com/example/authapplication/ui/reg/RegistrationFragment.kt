package com.example.authapplication.ui.reg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.authapplication.R
import com.example.authapplication.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pvCreatePassword.setHint(getString(R.string.create_password))
        binding.pvRepeat.setHint(getString(R.string.repeat_password))


        register()
    }

    private fun isPasswordCorrect(psw: String): Boolean {
        val isLengthCorrect = isLengthCorrect(psw)
        val containsLowerAndUppercase = containsLowerAndUppercase(psw)
        val containsDigit = containsDigit(psw)
        val containsSpecialSymbol = containsSpecialSymbol(psw)

        if (isLengthCorrect) {

        } else {

        }
        if (containsLowerAndUppercase) {

        } else {

        }
        if (containsDigit) {

        } else {

        }
        if (containsSpecialSymbol) {

        } else {

        }

        return isLengthCorrect && containsLowerAndUppercase && containsDigit && containsSpecialSymbol
    }

    private fun isLengthCorrect(input: String): Boolean {
        return input.length in 8..15
    }

    private fun containsLowerAndUppercase(input: String): Boolean {
        var hasLowerCase = false
        var hasUpperCase = false

        for (char in input) {
            if (char.isLowerCase()) hasLowerCase = true
            else if (char.isUpperCase()) hasUpperCase = true
        }
        return hasLowerCase && hasUpperCase
    }

    private fun containsDigit(input: String): Boolean {
        for (char in input) {
            if (char.isDigit()) return true
        }
        return false
    }

    private fun containsSpecialSymbol(input: String): Boolean {
        val specialSymbolsRegex = Regex("[!\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~]")

        return input.contains(specialSymbolsRegex)
    }

    private fun checkEmail(): Boolean {
        return true
    }

    private fun register() {
        val login = binding.etRegLogin.text.toString()
        val password = binding.pvCreatePassword.getText()
        binding.btnReg.setOnClickListener {
            if (checkEmail() && login.isNotEmpty() && isPasswordCorrect(password)) {

            }
        }
    }

}