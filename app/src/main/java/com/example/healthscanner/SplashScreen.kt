package com.example.healthscanner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Delay for 2 seconds and then start the main activity
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            delay(3000)
            startActivity(Intent(this@SplashScreen, Login::class.java))
            finish()
        }

    }
}