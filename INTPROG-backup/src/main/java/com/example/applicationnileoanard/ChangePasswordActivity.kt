package com.example.applicationnileoanard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.change_password)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val newPasswordEditText = findViewById<TextInputEditText>(R.id.new_password)
        val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.confirm_password)
        val saveButton = findViewById<MaterialButton>(R.id.btn_save_changes)

        saveButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (newPassword.isBlank() || confirmPassword.isBlank()) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else if (newPassword != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("user_password", newPassword)
                    apply()
                }

                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
