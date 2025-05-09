package com.example.applicationnileoanard

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val flashcardsButton = findViewById<LinearLayout>(R.id.flashcards)
        val challengesButton = findViewById<LinearLayout>(R.id.challenges)
        val learningButton = findViewById<LinearLayout>(R.id.learning_history)
        val notesButton = findViewById<LinearLayout>(R.id.notes)
        val profileButton = findViewById<LinearLayout>(R.id.profile)
        val settingsButton = findViewById<LinearLayout>(R.id.settings)

        flashcardsButton.setOnClickListener {
            val intent = Intent(this, FlashcardsActivity::class.java)
            startActivity(intent)
            finish()
        }

        learningButton.setOnClickListener {
            val intent = Intent(this, LearningActivity::class.java)
            startActivity(intent)
            finish()
        }

        notesButton.setOnClickListener {
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
            finish()
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}