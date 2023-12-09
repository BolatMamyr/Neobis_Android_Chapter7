package com.example.authapplication.ui.reg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.authapplication.R
import com.example.authapplication.databinding.FragmentSignInBinding
import com.example.authapplication.managers.UserManager
import com.example.authapplication.model.ApiResponse
import com.example.authapplication.other.navigate
import com.example.authapplication.other.toast
import com.example.authapplication.repo.AuthRepository
import com.example.authapplication.ui.reg.viewmodels.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SignInViewModel>()

    @Inject
    lateinit var userManager: UserManager

    @Inject
    lateinit var repo: AuthRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!userManager.getUser().isNullOrEmpty()) {
            navigate(R.id.action_signInFragment_to_mainFragment)
        }
        binding.btnGoToReg.setOnClickListener {
            navigate(R.id.action_signInFragment_to_registrationFragment)
        }

        binding.btnSignIn.setOnClickListener {
            val username = binding.etLogin.text.toString()
            val password = binding.passwordView.getText()

            viewModel.createAuthToken(username, password)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signInState.collectLatest {
                when (it) {
                    ApiResponse.Loading -> {
                        enableButton(false)
                    }

                    is ApiResponse.Error -> {
                        enableButton(true)
                        val msg = it.errorResponse.message
                        toast(msg)
                    }

                    is ApiResponse.Exception -> {
                        enableButton(true)
                        toast(getString(R.string.something_went_wrong))
                    }

                    is ApiResponse.Success -> {
                        enableButton(true)
                        val token = it.data.token
                        if (!token.isNullOrEmpty()) {
                            userManager.saveUser(token)
                            navigate(R.id.action_signInFragment_to_mainFragment)
                        }
                    }
                }
            }
        }
    }

    private fun enableButton(value: Boolean) {
        binding.btnSignIn.isEnabled = value
    }
}