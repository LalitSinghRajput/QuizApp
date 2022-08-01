package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // for removing buttons
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        Handler(Looper.getMainLooper()).postDelayed({
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

        logo.animate().apply {
            duration = 1000

            rotationYBy(180f)
            rotation(360f)
            scaleX(1.1f)
            scaleY(1.1f)

        }.withEndAction {
            logo.animate().apply {
                duration = 1000
                scaleX(0.9f)
                scaleY(0.9f)
            }.start()
        }

        name_title.animate().apply {
            duration = 1000
            scaleX(1.3f)
            scaleY(1.3f)

        }.withEndAction {
            name_title.animate().apply {
                duration = 1000
                scaleX(1f)
                scaleY(1f)
            }.start()
        }
    }
}