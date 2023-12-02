package com.example.authapplication.custom_view

import android.content.Context
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.authapplication.R
import com.example.authapplication.databinding.ViewPasswordBinding

class PasswordView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
): ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding = ViewPasswordBinding.inflate(LayoutInflater.from(context), this, true)

    private var isPasswordHidden = true
    init {
        binding.ivShowPassword.setOnClickListener {
            if (isPasswordHidden) {
                // for saving cursor position while doing transformation below(hiding/showing password)
                val selection = binding.etPassword.selectionEnd
                binding.ivShowPassword.setImageResource(R.drawable.ic_eye_unhidden)
                binding.etPassword.transformationMethod = null
                binding.etPassword.setSelection(selection)
                isPasswordHidden = false
            } else {
                val selection = binding.etPassword.selectionEnd
                binding.ivShowPassword.setImageResource(R.drawable.ic_eye)
                binding.etPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.etPassword.setSelection(selection)
                isPasswordHidden = true
            }
        }
    }

    fun setHint(text: String) {
        binding.etPassword.hint = text
    }

    fun getText() = binding.etPassword.text.toString()

    fun getEditText() = binding.etPassword
}