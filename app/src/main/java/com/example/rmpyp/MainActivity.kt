package com.example.rmpyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var endButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var questionTextView: TextView
    private var countAnswer = 0

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)
        endButton = findViewById(R.id.end_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { _: View ->
            checkAnswer(true)
            quizViewModel.moveToNext()
            updateQuestion()
        }

        falseButton.setOnClickListener { _: View ->
            checkAnswer(false)
            quizViewModel.moveToNext()
            updateQuestion()
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        backButton.setOnClickListener {
            quizViewModel.moveToBack()
            updateQuestion()
        }

        endButton.setOnClickListener{
            endTest()
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }


    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer) {
            countAnswer += 1
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
        if(quizViewModel.currentIndex == quizViewModel.questionBank.size - 1) {
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }
        if(quizViewModel.currentIndex == quizViewModel.questionBank.size) {
            countAnswer = 0
        }
    }

    private fun endTest() {
        val bundle = Bundle()
        bundle.putString("result", countAnswer.toString())

        var intent = Intent(this, SecondActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }


}

