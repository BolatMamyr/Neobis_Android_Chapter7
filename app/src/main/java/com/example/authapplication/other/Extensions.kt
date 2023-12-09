package com.example.authapplication.other

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

inline fun Fragment.MyAlertDialog(func: MyAlertDialog.() -> Unit) =
    MyAlertDialog(this.requireContext()).apply {
        func()
    }.create()

fun Fragment.toast(msg: String) =
    Toast.makeText(this.requireContext(), msg, Toast.LENGTH_SHORT).show()

fun Fragment.navigate(directions: NavDirections) = this.findNavController().navigate(directions)
fun Fragment.navigate(action: Int) = this.findNavController().navigate(action)