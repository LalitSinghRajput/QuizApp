package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_difficulty_mode_selection.*


class difficulty_mode_selection : AppCompatActivity() {
    var difficulty = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty_mode_selection)

        val prefs = getSharedPreferences("quiz", MODE_PRIVATE)
        val category =
            prefs.getString("category", "No category")
//        textView2.setText(category)

        easy_btn.setOnClickListener {
            resetAll()
            doWorkForEasy()
        }
        easyCard.setOnClickListener {
            resetAll()
            doWorkForEasy()
        }
        easy_title.setOnClickListener {
            resetAll()
            doWorkForEasy()
        }

        medium_btn.setOnClickListener {
            resetAll()
            doWorkForMedium()
        }
        medium_title.setOnClickListener {
            resetAll()
            doWorkForMedium()
        }
        mediumCard.setOnClickListener {
            resetAll()
            doWorkForMedium()
        }

        hard_btn.setOnClickListener {
            resetAll()
            doWorkForHard()
        }
        hard_title.setOnClickListener {
            resetAll()
            doWorkForHard()
        }
        hardCard.setOnClickListener {
            resetAll()
            doWorkForHard()
        }

        difficultyNextBtn.setOnClickListener {
            if (difficulty.isEmpty()) {
                Toast.makeText(this, "Select one of the difficulty mode", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Difficulty: " + difficulty, Toast.LENGTH_SHORT).show()
            }
        }

    }


    //    FUNCTIONS
    private fun resetAll() {
        easy_btn.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_circle_disable)))
        medium_btn.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_circle_disable)))
        hard_btn.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_circle_disable)))

        easy_title.setTypeface(null, Typeface.NORMAL)
        medium_title.setTypeface(null, Typeface.NORMAL)
        hard_title.setTypeface(null, Typeface.NORMAL)

        easyCard.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_card_disable)))
        mediumCard.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_card_disable)))
        hardCard.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_card_disable)))
    }

    private fun doWorkForEasy() {
        difficulty = "easy"
        easy_btn.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_circle_enable)))
        easy_title.setTypeface(null, Typeface.BOLD)
        easyCard.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_card_enable)))
    }

    private fun doWorkForMedium() {
        difficulty = "medium"
        medium_btn.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_circle_enable)))
        medium_title.setTypeface(null, Typeface.BOLD)
        mediumCard.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_card_enable)))
    }

    private fun doWorkForHard() {
        difficulty = "hard"
        hard_btn.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_circle_enable)))
        hard_title.setTypeface(null, Typeface.BOLD)
        hardCard.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_card_enable)))
    }
}