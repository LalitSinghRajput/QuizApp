package com.example.quizapp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView
import androidx.core.view.marginLeft
import android.os.Build

import android.graphics.drawable.GradientDrawable





class MainActivity : AppCompatActivity() {

    private var category: String = ""
    private val categoryCard =
        mapOf(
            R.id.science_card to "science",
            R.id.computer_card to "computer",
            R.id.sports_card to "sports",
            R.id.animals_card to "animals",
            R.id.music_card to "music",
            R.id.geography_card to "geography",
            R.id.book_card to "book",
            R.id.vehicle_card to "vehicle",
            R.id.videoGame_card to "videoGame",
            R.id.history_card to "history",
            R.id.cartoon_card to "cartoon",
            R.id.boardGame_card to "boardGame",

            );

    private val categoryTitle = mapOf(
        "science" to R.id.science_title,
        "computer" to R.id.computer_title,
        "sports" to R.id.sports_title,
        "animals" to R.id.animals_title,
        "music" to R.id.music_title,
        "geography" to R.id.geography_title,
        "book" to R.id.book_title,
        "vehicle" to R.id.vehicle_title,
        "videoGame" to R.id.vedioGame_title,
        "history" to R.id.history_title,
        "cartoon" to R.id.cartoon_title,
        "boardGame" to R.id.boardGame_title,
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // for removing buttons
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        resetAll()

        for (key in categoryCard.keys) {
            val currCategoryCard = findViewById<CardView>(key)
            currCategoryCard.setOnClickListener {
                category = categoryCard[key].toString()

                // reset
//                resetTitle()
                resetAll()

                val title = categoryTitle[category]!!.toInt()
                val currTitle = findViewById<TextView>(title)
                currTitle.setTypeface(null, Typeface.BOLD);

                val card = findViewById<CardView>(key)
                card.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_card_enable)))
                card.cardElevation = 30.0f
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

    private fun resetAll() {
        // reset card
        for (key in categoryCard.keys) {
            val card = findViewById<CardView>(key)
            card.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.difficulty_card_disable)))
            card.cardElevation = 10.0f


        }
        // resset title
        for (key in categoryTitle.keys) {
            val title = findViewById<TextView>(categoryTitle[key]!!.toInt())
            title.setTypeface(null, Typeface.NORMAL)
        }
    }

}