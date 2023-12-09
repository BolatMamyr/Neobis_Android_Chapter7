package com.example.authapplication.managers

import android.content.SharedPreferences
import javax.inject.Inject

class PrefManager @Inject constructor(private val pref: SharedPreferences) {

    fun getString(key: String, defaultValue: String = ""): String? {
        return pref.getString(key, defaultValue)
    }

    fun putString(key: String, value: String) {
        pref.edit().putString(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int = -1): Int {
        return pref.getInt(key, defaultValue)
    }

    fun remove(key: String) {
        pref.edit().remove(key).apply()
    }

    fun putInt(key: String, value: Int) {
        pref.edit().putInt(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return pref.getBoolean(key, defaultValue)
    }

    fun putBoolean(key: String, value: Boolean) {
        pref.edit().putBoolean(key, value).apply()
    }

    // Add similar functions for other data types as needed

    // For example, for Long:
    fun getLong(key: String, defaultValue: Long = -1L): Long {
        return pref.getLong(key, defaultValue)
    }

    fun putLong(key: String, value: Long) {
        pref.edit().putLong(key, value).apply()
    }

    // For Float:
    fun getFloat(key: String, defaultValue: Float = -1.0f): Float {
        return pref.getFloat(key, defaultValue)
    }

    fun putFloat(key: String, value: Float) {
        pref.edit().putFloat(key, value).apply()
    }
}
