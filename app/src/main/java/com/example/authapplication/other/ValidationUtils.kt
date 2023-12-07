package com.example.authapplication.other

import android.util.Patterns
import java.util.regex.Pattern

class ValidationUtils {
    companion object {
        fun isPasswordCorrect(psw: String): Boolean {
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

        fun isEmailValid(input: String): Boolean {
            if (input.isEmpty()) return false
            return Patterns.EMAIL_ADDRESS.matcher(input).matches()
        }
    }
}