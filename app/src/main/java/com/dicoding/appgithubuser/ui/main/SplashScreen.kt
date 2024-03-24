package com.dicoding.appgithubuser.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dicoding.appgithubuser.R

class SplashScreen : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
                Handler().postDelayed({
                    // Membuat intent untuk navigasi ke MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Menutup SplashScreen setelah beralih ke MainActivity
                }, SPLASH_TIME_OUT)
    }
}