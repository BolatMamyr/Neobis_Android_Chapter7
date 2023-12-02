package com.example.authapplication.ui.reg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.authapplication.R
import com.example.authapplication.databinding.FragmentRegistrationBinding
import com.example.authapplication.model.ResponseState

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tbReg.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        setHints()
        observeRegistration()
        register()

        addTextWatcher(binding.etRegEmail)
        addTextWatcher(binding.etRegLogin)
        addTextWatcher(binding.pvCreatePassword.getEditText())
        addTextWatcher(binding.pvRepeat.getEditText())
        binding.pvCreatePassword.getEditText().addTextChangedListener {
            it?.toString()?.let { psw ->
                val isLengthCorrect = viewModel.isLengthCorrect(psw)
                val containsLowerAndUppercase = viewModel.containsLowerAndUppercase(psw)
                val containsDigit = viewModel.containsDigit(psw)
                val containsSpecialSymbol = viewModel.containsSpecialSymbol(psw)

                if (isLengthCorrect) {
                    binding.tvLength.text = getString(R.string.password_length_warning) + "\u2705"
                    binding.tvLength.setTextColor(resources.getColor(R.color.green, null))
                } else {
                    binding.tvLength.text = getString(R.string.password_length_warning) + "\u274C"
                    binding.tvLength.setTextColor(resources.getColor(R.color.red, null))
                }
                if (containsLowerAndUppercase) {
                    binding.tvLowerUppercase.text =
                        getString(R.string.password_lower_upper_case_warning) + "\u2705"
                    binding.tvLowerUppercase.setTextColor(resources.getColor(R.color.green, null))
                } else {
                    binding.tvLowerUppercase.text =
                        getString(R.string.password_lower_upper_case_warning) + "\u274C"
                    binding.tvLowerUppercase.setTextColor(resources.getColor(R.color.red, null))
                }
                if (containsDigit) {
                    binding.tvDigit.text = getString(R.string.password_digit_warning) + "\u2705"
                    binding.tvDigit.setTextColor(resources.getColor(R.color.green, null))
                } else {
                    binding.tvDigit.text = getString(R.string.password_digit_warning) + "\u274C"
                    binding.tvDigit.setTextColor(resources.getColor(R.color.red, null))
                }
                if (containsSpecialSymbol) {
                    binding.tvSpecialChar.text =
                        getString(R.string.password_special_char_warning) + "\u2705"
                    binding.tvSpecialChar.setTextColor(resources.getColor(R.color.green, null))
                } else {
                    binding.tvSpecialChar.text =
                        getString(R.string.password_special_char_warning) + "\u274C"
                    binding.tvSpecialChar.setTextColor(resources.getColor(R.color.red, null))
                }
            }
        }

        binding.pvRepeat.getEditText().addTextChangedListener {
            binding.tvRepeatPasswordError.isVisible = false
        }
    }

    // Enables button if all fields are filled and password is correct.
    private fun addTextWatcher(editText: EditText) {
        editText.addTextChangedListener {
            val email = binding.etRegEmail.text.toString()
            val login = binding.etRegLogin.text.toString()
            val password = binding.pvCreatePassword.getText()
            val repeatPassword = binding.pvRepeat.getText()

            val isLengthCorrect = viewModel.isLengthCorrect(password)
            val containsLowerAndUppercase = viewModel.containsLowerAndUppercase(password)
            val containsDigit = viewModel.containsDigit(password)
            val containsSpecialSymbol = viewModel.containsSpecialSymbol(password)

            val isPasswordCorrect = isLengthCorrect && containsLowerAndUppercase
                    && containsDigit && containsSpecialSymbol

            if (email.isNotEmpty() && login.isNotEmpty() && isPasswordCorrect && repeatPassword.isNotEmpty()) {
                enableButton(true)
            } else {
                enableButton(false)
            }
        }
    }
    
    private fun enableButton(value: Boolean) {
        binding.btnReg.apply {
            isEnabled = value
            val btnColor = if (value) {
                resources.getColor(R.color.black, null)
            } else {
                resources.getColor(R.color.light_gray2, null)
            }
            val textColor = if (value) {
                resources.getColor(R.color.white, null)
            } else {
                resources.getColor(R.color.gray, null)
            }
            setBackgroundColor(btnColor)
            setTextColor(textColor)
        }
    }


    private fun observeRegistration() {
        viewModel.registrationState.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    val email = it.data
                    val action = RegistrationFragmentDirections
                        .actionRegistrationFragmentToEmailConfirmationFragment(email)
                    findNavController().navigate(action)
                }

                else -> Unit
            }
        }

        viewModel.emailValidState.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Error -> {
                    binding.etRegEmail.error = "Email format is not valid"
                }

                else -> Unit
            }
        }

        viewModel.passwordMatchState.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Error -> {
                    binding.tvRepeatPasswordError.isVisible = true
                    binding.tvRepeatPasswordError.requestFocus()
                }

                else -> {
                    binding.tvRepeatPasswordError.isVisible = false
                }
            }
        }
    }

    private fun setHints() {
        binding.pvCreatePassword.setHint(getString(R.string.create_password))
        binding.pvRepeat.setHint(getString(R.string.repeat_password))
    }

    private fun register() {
        binding.btnReg.setOnClickListener {
            val email = binding.etRegEmail.text.toString()
            val login = binding.etRegLogin.text.toString()
            val password = binding.pvCreatePassword.getText()
            val repeatPassword = binding.pvRepeat.getText()

            viewModel.register(email, login, password, repeatPassword)
        }
    }

}