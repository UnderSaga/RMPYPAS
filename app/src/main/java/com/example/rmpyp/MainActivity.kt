package com.example.rmpyp

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
    private lateinit var nextButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var resTextView: TextView
    private lateinit var tryCountView: TextView
    private lateinit var resView1: TextView
    private lateinit var againButton: Button
    private var countAnswer: Int = 0
    private var againTry: Int = 0

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        resView1 = findViewById(R.id.res_view1)
        tryCountView = findViewById(R.id.try_text_view)
        againButton = findViewById(R.id.again_button)
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)
        questionTextView = findViewById(R.id.question_text_view)
        resTextView = findViewById(R.id.res_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            quizViewModel.moveToNext()
            updateQuestion()
        }
        falseButton.setOnClickListener { view: View ->
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

        againButton.setOnClickListener {
            countAnswer = 0
            againTry += 1
            tryCountView.text = againTry.toString()
            trueButton.isEnabled = true
            falseButton.isEnabled = true
            resTextView.visibility = View.INVISIBLE
            quizViewModel.moveToFirst()
            updateQuestion()
            againButton.visibility = View.INVISIBLE
            resView1.visibility = View.INVISIBLE
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
        resTextView.visibility = View.INVISIBLE
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        if (userAnswer == correctAnswer) {
            countAnswer += 1
            resTextView.text = countAnswer.toString()
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
        if(quizViewModel.currentIndex == quizViewModel.questionBank.size - 1) {
            resTextView.visibility = View.VISIBLE
            resView1.visibility = View.VISIBLE
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            countAnswer = 0
            againButton.visibility = View.VISIBLE
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }


}

