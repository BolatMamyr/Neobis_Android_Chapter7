package com.example.authapplication.repo

import com.example.authapplication.model.TokenRequestBody
import com.example.authapplication.model.CreateUserRequestBody
import com.example.authapplication.model.TokenResponse
import com.example.authapplication.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: ApiService) {

    suspend fun createNewUser(userInfo: CreateUserRequestBody): Response<Any> {
        return api.createNewUser(userInfo)
    }

    suspend fun createAuthToken(tokenRequestBody: TokenRequestBody): Response<Any> {
        return api.createAuthToken(tokenRequestBody)
    }

}