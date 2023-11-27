package com.example.authapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        overrideActivityTransition(
                            OVERRIDE_TRANSITION_CLOSE,
                            com.google.android.material.R.anim.abc_fade_in,
                            com.google.android.material.R.anim.abc_fade_out
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        overridePendingTransition(
                            com.google.android.material.R.anim.abc_fade_in,
                            com.google.android.material.R.anim.abc_fade_out
                        )
                    }
                    finish()
                }
            },
            2000
        )
    }
}