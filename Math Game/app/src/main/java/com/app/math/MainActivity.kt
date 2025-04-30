package com.app.math

import android.content.ContentProviderOperation
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var Addition : TextView
    private lateinit var Subtraction : TextView
    private lateinit var Multiplication : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Addition = findViewById(R.id.TextViewAddition)
        Subtraction = findViewById(R.id.TextViewSubtraction)
        Multiplication = findViewById(R.id.TextViewMultiplication)
        Addition.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("operation", "addition")
            startActivity(intent)
        }
        Subtraction.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("operation", "subtraction")
            startActivity(intent)
        }
        Multiplication.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("operation", "multiplication")
            startActivity(intent)
        }
    }
}