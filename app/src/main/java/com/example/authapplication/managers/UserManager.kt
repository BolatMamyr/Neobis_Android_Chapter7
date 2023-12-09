package com.example.authapplication.managers

import com.example.authapplication.model.User
import javax.inject.Inject

class UserManager @Inject constructor(private val prefManager: PrefManager) {

    companion object {
        const val KEY_USER = "KEY_USER_INFO"
        const val KEY_TOKEN = "KEY_TOKEN"
        const val KEY_USER_ID = "KEY_USER_ID"
        const val KEY_USERNAME = "USERNAME"
        const val KEY_EMAIL = "KEY_EMAIL"

    }
//    fun getUser(): User? {
//        val id = prefManager.getInt(KEY_USER_ID)
//        if (id == -1) return null
//        val email = prefManager.getString(KEY_EMAIL) ?: ""
//        val username = prefManager.getString(KEY_USERNAME) ?: ""
//
//        return User(id, email, username)
//    }
    fun getUser(): String? = prefManager.getString(KEY_TOKEN)


    fun saveUser(token: String) {
        prefManager.putString(KEY_TOKEN, token)
    }

    fun removeUser() {
        prefManager.remove(KEY_TOKEN)
    }
}