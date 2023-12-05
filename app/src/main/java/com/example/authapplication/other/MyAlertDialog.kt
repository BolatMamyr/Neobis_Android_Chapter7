package com.example.authapplication.other

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.example.authapplication.databinding.LayoutAlertDialogBinding

class MyAlertDialog(context: Context) : AlertDialog(context) {

    private var binding: LayoutAlertDialogBinding? = null

    init {
        binding = LayoutAlertDialogBinding.inflate(LayoutInflater.from(context))
        setView(binding?.root)
    }

    fun setTitle(text: String) {
        binding?.tvAlertTitle?.text = text
    }

    fun setMessage(text: String) {
        binding?.tvAlertMessage?.text = text
    }

    fun setPositiveButton(text: String, func: () -> Unit) {
        binding?.btnPositive?.text = text
        binding?.btnPositive?.setOnClickListener {
            func()
        }
    }
    fun setNegativeButton(text: String, func: () -> Unit) {
        binding?.btnNegative?.apply {
            this.text = text
            isVisible = true
            setOnClickListener {
                func()
            }
        }
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }
}