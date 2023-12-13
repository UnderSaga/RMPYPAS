package com.example.rmpyp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

private const val TAG = "SecondActivity"

class SecondActivity: AppCompatActivity() {
    private lateinit var againButton: Button
    private lateinit var resView: TextView


    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_second)

        againButton = findViewById(R.id.again_button)
        resView = findViewById(R.id.res_view)

        resView.text = intent.getStringExtra("result")

        againButton.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            quizViewModel.moveToFirst()
        }
    }
}