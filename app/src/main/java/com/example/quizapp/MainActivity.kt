package com.example.quizapp

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private var category: String = ""
    private val categoryCard =
        mapOf(
            R.id.science_card to "science",
            R.id.math_card to "math",
            R.id.sports_card to "sports",
            R.id.animals_card to "animals",
            R.id.music_card to "music",
            R.id.geography_card to "geography",
        );

    private val categoryTitle = mapOf(
        "science" to R.id.science_title,
        "math" to R.id.math_title,
        "sports" to R.id.sports_title,
        "animals" to R.id.animals_title,
        "music" to R.id.music_title,
        "geography" to R.id.geography_title,
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }


        for (key in categoryCard.keys) {
            val currCategoryCard = findViewById<CardView>(key)
            currCategoryCard.setOnClickListener {
                category = categoryCard[key].toString()

                // reset
                resetTitle()

                val title = categoryTitle[category]!!.toInt()
                val currTitle = findViewById<TextView>(title)
                currTitle.setTypeface(null, Typeface.BOLD);
            }
        }

        categoryNextBtn.setOnClickListener {
            if (category.isEmpty()) {
                Toast.makeText(this, "Select one of the category", Toast.LENGTH_SHORT).show()
            } else {


                val editor = getSharedPreferences("quiz", MODE_PRIVATE).edit()
                editor.putString("category", category)
                editor.apply()
//                Toast.makeText(this, "category: " + category, Toast.LENGTH_SHORT).show()
                // go to difficulty mode activity
                val intent = Intent(this, difficulty_mode_selection::class.java)
                startActivity(intent)
            }
        }
    }

    private fun resetTitle() {
        for (key in categoryTitle.keys) {
            val title = findViewById<TextView>(categoryTitle[key]!!.toInt())
            title.setTypeface(null, Typeface.NORMAL)
        }
    }

}