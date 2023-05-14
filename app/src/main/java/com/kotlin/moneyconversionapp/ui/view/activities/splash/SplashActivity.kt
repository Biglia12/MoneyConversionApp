package com.kotlin.moneyconversionapp.ui.view.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kotlin.moneyconversionapp.R
import com.kotlin.moneyconversionapp.ui.view.activities.MainActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        screenSplash.setKeepOnScreenCondition { true }

        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

    }
}