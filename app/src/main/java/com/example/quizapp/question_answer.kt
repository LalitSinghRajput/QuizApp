package com.example.quizapp

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_question_answer.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class question_answer : AppCompatActivity() {

    private val optionCard: List<Int> =
        listOf(R.id.option1_card, R.id.option2_card, R.id.option3_card, R.id.option4_card)
    private val options: List<Int> = listOf(R.id.option1, R.id.option2, R.id.option3, R.id.option4)
    private var ind = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)

        // for removing buttons
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }


        val prefs = getSharedPreferences("quiz", MODE_PRIVATE)
        val category = prefs.getString("category", "No category")
        val difficutly = prefs.getString("difficulty", "No difficulty")

        resetAllFeilds()
        resetOptionsCard()
        getJsonData()

    }

    // Functions
    private fun getJsonData() {
        val queue = Volley.newRequestQueue(this)
        val URL = "https://opentdb.com/api.php?amount=10&category=27&difficulty=easy&type=multiple"

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, URL, null,
            Response.Listener { response ->
//                Log.d("my_quiz", "getJsonData: " + response)

                var result = response.getJSONArray("results")
                setData(result)
                Log.d("my_quiz", "res: " + result)
//
            },
            Response.ErrorListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonRequest)
    }

    private fun setValues(response: JSONObject) {
        resetOptionsCard()
        question.text = response.getString("question").toString()

        val correctAns = response.getString("correct_answer").toString()
        val incArr = response.getJSONArray("incorrect_answers")

        var optionsArr = mutableListOf<String>()

        for (i in 0 until incArr.length()) {
            val item = incArr[i].toString()
            optionsArr.add(item)
        }
        optionsArr.add(correctAns)
        optionsArr.shuffle()

//        Log.d("my_quiz", "incArray: " + incArray)
        for (ind in options.indices) {
            val option = findViewById<TextView>(options[ind])
            option.setText(optionsArr[ind].toString())
        }
    }

    private fun setData(response: JSONArray) {
        val data = response
        Log.d("my_quiz", "res:106 " + data)
        var ansGiven = false

        // first
        setValues(data!![0] as JSONObject)
        questionNumber.setText((ind + 1).toString())

        // remaining
        ques_ans_nextBtn.setOnClickListener {
            ind++
            ansGiven = false
            if (ind < data!!.length()) {
                questionNumber.setText((ind + 1).toString())
                setValues(data!![ind] as JSONObject)
            } else
                Toast.makeText(this, "All Done", Toast.LENGTH_LONG).show()
        }


        for (index in optionCard.indices) {
            val option_card = findViewById<CardView>(optionCard[index])
            val option = findViewById<TextView>(options[index])
            option_card.setOnClickListener {
//                Toast.makeText(this, "You clicked on: " + option.text, Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, "correct: " + correctAns, Toast.LENGTH_LONG).show()
                val inputAns = option.text
                val correctAns = (data[ind] as JSONObject).getString("correct_answer")

                if (ansGiven == false) {
                    if (inputAns == correctAns) {
//                        Toast.makeText(this, "Correct Ans", Toast.LENGTH_LONG).show()
                        option_card.setBackgroundTintList(
                            ColorStateList.valueOf(
                                resources.getColor(
                                    R.color.correct_option
                                )
                            )
                        )
                        val mediaPlayer = MediaPlayer.create(this, R.raw.correct_ans_sound)
                        mediaPlayer.start()
                    } else {
//                        Toast.makeText(this, "Wrong Ans", Toast.LENGTH_LONG).show()
                        option_card.setBackgroundTintList(
                            ColorStateList.valueOf(
                                resources.getColor(
                                    R.color.incorrect_option
                                )
                            )
                        )

                        // tell which one is correct
                        for (i in options.indices) {
                            val curr = findViewById<TextView>(options[i])
                            if (curr.text == correctAns) {
                                val optCard = findViewById<CardView>(optionCard[i])
                                optCard.setBackgroundTintList(
                                    ColorStateList.valueOf(
                                        resources.getColor(
                                            R.color.correct_option
                                        )
                                    )
                                )
                                curr.setTextColor(ContextCompat.getColor(this, R.color.white))
                                curr.setTypeface(null, Typeface.BOLD)

                            }
                        }
                        val mediaPlayer = MediaPlayer.create(this, R.raw.wrong_ans_sound)
                        mediaPlayer.start()
                    }
                    ansGiven = true
                    option.setTextColor(ContextCompat.getColor(this, R.color.white))
                    option.setTypeface(null, Typeface.BOLD)
                }

            }
        }
    }

    private fun resetAllFeilds() {
        question.setText("")
        for (id in options) {
            val option = findViewById<TextView>(id)
            option.setText("")
        }


    }

    private fun resetOptionsCard() {
        for (id in optionCard) {
            val option_card = findViewById<CardView>(id)
            option_card.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(R.color.white)))
        }
        for (id in options) {
            val option = findViewById<TextView>(id)
            option.setTextColor(ContextCompat.getColor(this, R.color.black))

        }
    }

}