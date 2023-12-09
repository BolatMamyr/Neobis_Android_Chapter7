package com.example.authapplication.ui.reg.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authapplication.model.ApiResponse
import com.example.authapplication.model.ErrorResponse
import com.example.authapplication.model.TokenRequestBody
import com.example.authapplication.model.TokenResponse
import com.example.authapplication.other.mylog
import com.example.authapplication.other.myloge
import com.example.authapplication.repo.AuthRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _signInState = MutableSharedFlow<ApiResponse<TokenResponse>>()
    val signInState: SharedFlow<ApiResponse<TokenResponse>> = _signInState

    fun createAuthToken(username: String, password: String) {
        viewModelScope.launch {
            _signInState.emit(ApiResponse.Loading)
            try {
                val body = TokenRequestBody(username, password)
                val response = authRepository.createAuthToken(body)

                mylog("createAuthToken: code=${response.code()}, message=${response.message()}")
                val gson = Gson()
                if (response.isSuccessful) {
                    val data = gson.fromJson(gson.toJson(response.body()), TokenResponse::class.java)

                    mylog("createAuthToken: SUCCESS: $data")
                    _signInState.emit(ApiResponse.Success(data))
                } else {
                    val errorBody = response.errorBody()!!
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val errorResponse: ErrorResponse = gson.fromJson(errorBody.charStream(), type)!!

                    mylog("createAuthToken: ERROR: $errorResponse")
                    _signInState.emit(ApiResponse.Error(errorResponse))
                }
            } catch (e: Exception) {
                myloge("createAuthToken: Exception = ${e.stackTraceToString()}")
                _signInState.emit(ApiResponse.Exception(e))
            }
        }
    }
}