package com.app.math

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import java.util.Locale
import kotlin.concurrent.timer
import kotlin.random.Random

class GameActivity: AppCompatActivity() {
    private lateinit var score : TextView
    private lateinit var lifeTime : TextView
    private lateinit var time : TextView
    private lateinit var question : TextView
    private lateinit var answer : EditText
    private lateinit var buttonOk : TextView
    private lateinit var buttonNext : TextView
    var scoreValue = 0
    var lifeTimeValue = 3
    var correctAnswer = 0
    lateinit var timer : CountDownTimer
    private val startTimerInMillis : Long = 20000
    private var timerLeftInMillis : Long = startTimerInMillis
    private var currentOperation = "addition"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        score = findViewById(R.id.tv_score)
        lifeTime = findViewById(R.id.tv_life)
        time = findViewById(R.id.tv_time)
        question = findViewById(R.id.tv_question)
        answer = findViewById(R.id.et_answer)
        buttonOk = findViewById(R.id.tv_ok)
        buttonNext = findViewById(R.id.tv_next)
        currentOperation = intent.getStringExtra("operation")?: "addition"
        gameQuestion()
        buttonOk.setOnClickListener {
            val userAnswer = answer.text.toString()
            if(userAnswer.isEmpty()){
                Toast.makeText(this, "Please input your answer", Toast.LENGTH_SHORT).show()
            }else{
                pauseTimer()
                val userAnswerInt = userAnswer.toInt()
                if(userAnswerInt == correctAnswer){
                    scoreValue = scoreValue + 10
                    question.text = "Correct Answer"
                    score.text = scoreValue.toString()


                }else{
                    lifeTimeValue = lifeTimeValue - 1
                    lifeTime.text = lifeTimeValue.toString()
                    question.text = "Wrong Answer"
                }
                answer.text.clear()

            }
        }
        buttonNext.setOnClickListener {
            pauseTimer()
            resetTimer()
            if(lifeTimeValue > 0){
                gameQuestion()
                answer.text.clear()
            }else{
                question.text = "Game Over"
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("score", scoreValue)
                startActivity(intent)
                finish()
            }
        }


    }
    fun gameQuestion(){
        val number1 = Random.nextInt(1, 100)
        val number2 = Random.nextInt(1, 100)
       when(currentOperation){
            "addition" ->{
                correctAnswer = number1 + number2
                question.text = "$number1 + $number2"
            }

            "subtraction" -> {
                correctAnswer = number1 - number2
                question.text = "$number1 - $number2"
            }
            "multiplication" -> {
                correctAnswer = number1 * number2
                question.text = "$number1 * $number2"
            }
       }
        startTimer()

    }
    fun startTimer(){
        timer = object : CountDownTimer(startTimerInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerLeftInMillis = millisUntilFinished
               // val secondsLeft = (timerLeftInMillis / 1000).toInt()
              //  time.text = secondsLeft.toString()
                updateText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()

                lifeTimeValue--
                lifeTime.text = lifeTimeValue.toString()
                question.text = "Time's up"
            }
        }.start()
    }
    fun updateText(){
        val secondsLeft = (timerLeftInMillis / 1000).toInt()
        time.text = String.format(Locale.getDefault(), "%02d", secondsLeft)
    }
    fun pauseTimer(){
        timer.cancel()
    }
    fun resetTimer(){
        timerLeftInMillis = startTimerInMillis
        updateText()
    }

}