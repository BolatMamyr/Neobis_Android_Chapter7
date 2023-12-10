package com.example.authapplication.managers

import javax.inject.Inject

class UserManager @Inject constructor(private val prefManager: PrefManager) {

    companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
    }

    fun getUser(): String? = prefManager.getString(KEY_TOKEN)


    fun saveUser(token: String) {
        prefManager.putString(KEY_TOKEN, token)
    }

    fun removeUser() {
        prefManager.remove(KEY_TOKEN)
    }
}