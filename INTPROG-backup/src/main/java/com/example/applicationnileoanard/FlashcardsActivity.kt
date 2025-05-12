package com.example.applicationnileoanard

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FlashcardsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_flashcards)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backBtn = findViewById<ImageView>(R.id.backButton)
        backBtn.setOnClickListener{
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }

        val title = findViewById<EditText>(R.id.title_name)
        val duration = findViewById<LinearLayout>(R.id.duration)
        val numOfItems = findViewById<LinearLayout>(R.id.no_items)
        val description = findViewById<TextView>(R.id.description)
        val shuffle = findViewById<ImageView>(R.id.shuffle)
        val editCards = findViewById<LinearLayout>(R.id.edit_flashcards)


    }
}