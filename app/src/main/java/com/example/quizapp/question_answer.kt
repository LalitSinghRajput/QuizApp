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
import java.net.URLDecoder
import java.util.*

class question_answer : AppCompatActivity() {

    private val optionCard: List<Int> =
        listOf(R.id.option1_card, R.id.option2_card, R.id.option3_card, R.id.option4_card)
    private val options: List<Int> = listOf(R.id.option1, R.id.option2, R.id.option3, R.id.option4)
    private var ind = 0
    private var attempted = 0
    private var notAttempted = 0
    private var noOfCorrectAns = 0
    private var noOfInCorrectAns = 0

    private val categoryId = mapOf(
        "science" to 17,
        "computer" to 18,
        "sports" to 21,
        "animals" to 27,
        "music" to 12,
        "geography" to 22,
        "book" to 10,
        "vehicle" to 28,
        "videoGame" to 15,
        "history" to 23,
        "cartoon" to 32,
        "boardGame" to 16,
    )
    lateinit var category: String
    lateinit var difficutly: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer)

        // for removing buttons
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }


        val prefs = getSharedPreferences("quiz", MODE_PRIVATE)
        category = prefs.getString("category", "No category").toString()
        difficutly = prefs.getString("difficulty", "No difficulty").toString()

        resetAllFeilds()
        resetOptionsCard()
        getJsonData()

    }

    // Functions
    private fun getJsonData() {
        val queue = Volley.newRequestQueue(this)
//        val categID = categoryId[category]
        val URL =
            "https://opentdb.com/api.php?amount=10&category=${categoryId[category]}&difficulty=${difficutly}&type=multiple&encode=url3986"

        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, URL, null,
            Response.Listener { response ->
//                Log.d("my_quiz", "getJsonData: " + response)

                var result = response.getJSONArray("results")
                setData(result)
//                Log.d("my_quiz", "res: " + result)
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
        var encodedQues = response.getString("question").toString()
        val ques: String = URLDecoder.decode(encodedQues, "UTF-8")
        Log.d("my_quiz", "Encoded String: " + encodedQues)
        Log.d("my_quiz", "Decoded String: " + ques)

        question.setText(ques)

        val encodedCorrectAns = response.getString("correct_answer").toString()
        val correctAns: String = URLDecoder.decode(encodedCorrectAns, "UTF-8")
        val incArr = response.getJSONArray("incorrect_answers")

        var optionsArr = mutableListOf<String>()

        for (i in 0 until incArr.length()) {
            val encodedItem = incArr[i].toString()
            val item: String = URLDecoder.decode(encodedItem, "UTF-8")
            optionsArr.add(item)
        }
        optionsArr.add(correctAns)
        optionsArr.shuffle()

//        Log.d("my_quiz", "incArray: " + incArray)
        for (ind in options.indices) {
            val option = findViewById<TextView>(options[ind])
            option.setText(optionsArr[ind])
        }
    }

    private fun setData(response: JSONArray) {
        val data = response
//        Log.d("my_quiz", "res:106 " + data)
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
            } else {
//                Toast.makeText(this, "All Done", Toast.LENGTH_LONG).show()
                Toast.makeText(
                    this,
                    "Correct: $noOfCorrectAns\nIncorrect: $noOfInCorrectAns\nScore Card soon implementing",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        for (index in optionCard.indices) {
            val option_card = findViewById<CardView>(optionCard[index])
            val option = findViewById<TextView>(options[index])
            option_card.setOnClickListener {
//                Toast.makeText(this, "You clicked on: " + option.text, Toast.LENGTH_SHORT).show()
//                Toast.makeText(this, "correct: " + correctAns, Toast.LENGTH_LONG).show()
                val inputAns = option.text
                val encodedCorrectAns = (data[ind] as JSONObject).getString("correct_answer")
                val correctAns: String = URLDecoder.decode(encodedCorrectAns, "UTF-8")

                if (ansGiven == false) {
                    if (inputAns == correctAns) {
//                        Toast.makeText(this, "Correct Ans", Toast.LENGTH_LONG).show()
                        noOfCorrectAns++
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
                        noOfInCorrectAns++
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
            option.setTypeface(null, Typeface.BOLD)

        }
    }

}