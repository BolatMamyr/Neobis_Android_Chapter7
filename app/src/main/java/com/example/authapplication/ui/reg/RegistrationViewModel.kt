package com.example.authapplication.ui.reg

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.authapplication.model.ResponseState
import com.example.authapplication.other.Constants
import java.util.regex.Pattern

class RegistrationViewModel : ViewModel() {

    private val _passwordMatchState = MutableLiveData<ResponseState<Unit>>()
    val passwordMatchState: LiveData<ResponseState<Unit>> = _passwordMatchState

    private val _emailValidState = MutableLiveData<ResponseState<Unit>>()
    val emailValidState: LiveData<ResponseState<Unit>> = _emailValidState

    private val _registrationState = MutableLiveData<ResponseState<String>>()
    val registrationState: LiveData<ResponseState<String>> = _registrationState

    private fun isPasswordCorrect(psw: String): Boolean {
        val isLengthCorrect = isLengthCorrect(psw)
        val containsLowerAndUppercase = containsLowerAndUppercase(psw)
        val containsDigit = containsDigit(psw)
        val containsSpecialSymbol = containsSpecialSymbol(psw)

        return isLengthCorrect && containsLowerAndUppercase && containsDigit && containsSpecialSymbol
    }

    fun isLengthCorrect(input: String): Boolean {
        return input.length in 8..15
    }

    fun containsLowerAndUppercase(input: String): Boolean {
        var hasLowerCase = false
        var hasUpperCase = false

        for (char in input) {
            if (char.isLowerCase()) hasLowerCase = true
            else if (char.isUpperCase()) hasUpperCase = true
        }
        return hasLowerCase && hasUpperCase
    }

    fun containsDigit(input: String): Boolean {
        for (char in input) {
            if (char.isDigit()) return true
        }
        return false
    }

    fun containsSpecialSymbol(input: String): Boolean {
        val special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]")
        return special.matcher(input).find()
    }

    private fun isEmailValid(input: String): Boolean {
        if (input.isEmpty()) return false
        return Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }

    fun register(email: String, login: String, password: String, repeatPassword: String) {
        val isEmailValid = isEmailValid(email)
        val isPasswordCorrect = isPasswordCorrect(password)
        val doesPasswordMatch = password == repeatPassword
        if (!isEmailValid) {
            _emailValidState.value = ResponseState.Error(Exception(Constants.NOT_VALID))
        }
        if (!doesPasswordMatch) {
            _passwordMatchState.value = ResponseState.Error(Exception(Constants.NOT_VALID))
        } else {
            _passwordMatchState.value = ResponseState.Success(Unit)
        }

        // no need to check login bcs button is enabled only if all fields are filled
        if (isEmailValid && isPasswordCorrect && doesPasswordMatch) {
            _registrationState.value = ResponseState.Success(email)
        }
    }
}