package com.example.authapplication.other

import android.content.Context
import androidx.fragment.app.Fragment

inline fun Fragment.MyAlertDialog(func: MyAlertDialog.() -> Unit) =
    MyAlertDialog(this.requireContext()).apply {
        func()
    }.create()