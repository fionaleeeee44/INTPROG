package com.example.applicationnileoanard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_page) // Ensure correct XML is set

        val confirmLogoutButton = findViewById<Button>(R.id.button_logout)
        confirmLogoutButton.setOnClickListener {
            val intent = Intent(this, LogoutActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        val backBtn = findViewById<Button>(R.id.button_Back)
        backBtn.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

        val aboutDevelopers = findViewById<LinearLayout>(R.id.about_developers)
        aboutDevelopers.setOnClickListener {
            val intent = Intent(this, DevelopersActivity::class.java) // Ensure DevelopersActivity exists
            startActivity(intent)
        }
    }
}