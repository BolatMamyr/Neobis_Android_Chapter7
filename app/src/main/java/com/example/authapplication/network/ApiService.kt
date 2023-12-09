package com.example.authapplication.network

import com.example.authapplication.model.ApiResponse
import com.example.authapplication.model.TokenRequestBody
import com.example.authapplication.model.CreateUserRequestBody
import com.example.authapplication.model.CreateUserResponse
import com.example.authapplication.model.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("/auth/registration")
    suspend fun createNewUser(
        @Body createUserRequestBody: CreateUserRequestBody
    ): Response<Any>

    /**
     * Creates and returns token
     */
    @POST("/auth/authenticate")
    suspend fun createAuthToken(
        @Body tokenRequestBody: TokenRequestBody
    ): Response<TokenResponse>


    // TODO: ASK HOW TO ACTIVATE
    @GET("/auth/activate")
    suspend fun activateUserAccount(
        @Query("token") token: String
    ): Response<Unit>

}