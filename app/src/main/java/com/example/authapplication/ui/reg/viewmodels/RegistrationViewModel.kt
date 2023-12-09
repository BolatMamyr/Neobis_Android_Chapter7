package com.example.authapplication.ui.reg.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authapplication.model.ApiResponse
import com.example.authapplication.model.CreateUserRequestBody
import com.example.authapplication.model.CreateUserResponse
import com.example.authapplication.model.ErrorResponse
import com.example.authapplication.model.ResponseState
import com.example.authapplication.other.Constants
import com.example.authapplication.other.ValidationUtils
import com.example.authapplication.other.mylog
import com.example.authapplication.other.myloge
import com.example.authapplication.other.mylogi
import com.example.authapplication.repo.AuthRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    private val _passwordMatchState = MutableLiveData<ResponseState<Unit>>()
    val passwordMatchState: LiveData<ResponseState<Unit>> = _passwordMatchState

    private val _emailValidState = MutableLiveData<ResponseState<Unit>>()
    val emailValidState: LiveData<ResponseState<Unit>> = _emailValidState

    private val _registrationState = MutableLiveData<ResponseState<CreateUserRequestBody>>()
    val registrationState: LiveData<ResponseState<CreateUserRequestBody>> = _registrationState

    private val _createNewUserState = MutableLiveData<ApiResponse<CreateUserResponse>>()
    val createNewUserState: LiveData<ApiResponse<CreateUserResponse>> = _createNewUserState

    // checks email, login and password for correctness
    fun validateRegistrationData(
        email: String,
        username: String,
        password: String,
        repeatPassword: String
    ) {
        val isEmailValid = ValidationUtils.isEmailValid(email)
        val isPasswordCorrect = ValidationUtils.isPasswordCorrect(password)
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
            _registrationState.value = ResponseState.Success(CreateUserRequestBody(email, username, password))
        }
    }

    fun createNewUser(createUserRequestBody: CreateUserRequestBody) {
        viewModelScope.launch {
            _createNewUserState.value = ApiResponse.Loading
            try {
                mylog("createNewUser START")
                val response = authRepository.createNewUser(createUserRequestBody)
                mylog("createNewUser: code=${response.code()}, msg=${response.message()} isSuccess=${response.isSuccessful}")
                val gson = Gson()
                if (response.isSuccessful) {
                    val data = gson.fromJson(gson.toJson(response.body()), CreateUserResponse::class.java)

                    mylogi("createNewUser: SUCCESS = $data")
                    _createNewUserState.value = ApiResponse.Success(data)
                } else {
                    val errorBody = response.errorBody()!!
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val errorResponse: ErrorResponse = gson.fromJson(errorBody.charStream(), type)!!

                    myloge("createNewUser: ERROR : $errorResponse")
                    _createNewUserState.value = ApiResponse.Error(errorResponse)
                }

            } catch (e: Exception) {
                _createNewUserState.value = ApiResponse.Exception(e)
                myloge("createNewUser : Exception = ${e.stackTraceToString()}")
            }
        }
    }
}