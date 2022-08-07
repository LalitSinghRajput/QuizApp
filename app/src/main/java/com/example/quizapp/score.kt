package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.close
import android.view.View
import kotlinx.android.synthetic.main.activity_score.*

class score : AppCompatActivity() {

    private var correctAns = 0
    private var inCorrectAns = 0
    private var unAttemptedAns = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // for removing buttons
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }


        val prefs = getSharedPreferences("quiz", MODE_PRIVATE)
        correctAns = prefs.getInt("correctAns", 0)
        inCorrectAns = prefs.getInt("inCorrectAns", 0)
        unAttemptedAns = 10 - (correctAns + inCorrectAns)

        correctText.text = correctAns.toString()
        incorrectText.text = inCorrectAns.toString()
        unattemptedText.text = unAttemptedAns.toString()
        score_text.text = correctAns.toString()

        exitBtn.setOnClickListener {
            finishAffinity();
            System.exit(0);
        }
        playAgainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}