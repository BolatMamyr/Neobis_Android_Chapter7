package com.example.authapplication.di

import android.content.Context
import android.content.SharedPreferences
import com.example.authapplication.managers.PrefManager
import com.example.authapplication.managers.UserManager
import com.example.authapplication.network.ApiService
import com.example.authapplication.other.Constants.BASE_URL
import com.example.authapplication.repo.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getApiService(): ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Provides
    @Singleton
    fun getAuthRepository(api: ApiService): AuthRepository = AuthRepository(api)

    @Provides
    @Singleton
    fun getSharedPreferences(@ApplicationContext context: Context) =
        context.getSharedPreferences("AuthAppPrefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun getPrefManager(pref: SharedPreferences) = PrefManager(pref)

    @Provides
    @Singleton
    fun getUserManager(prefManager: PrefManager) = UserManager(prefManager)



}