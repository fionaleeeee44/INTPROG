package com.example.applicationnileoanard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password)

        val emailInput = findViewById<EditText>(R.id.email_input)
        val resetPasswordButton = findViewById<MaterialButton>(R.id.btn_reset_password)
        val backPassword = findViewById<ImageButton>(R.id.btn_back)

        backPassword.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        resetPasswordButton.setOnClickListener {
            val enteredEmail = emailInput.text.toString().trim()
            val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val savedEmail = sharedPref.getString("user_email", null)

            if (enteredEmail.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else if (enteredEmail == savedEmail) {
                // Email is valid, navigate to OTP verification
                val intent = Intent(this, OtpActivity::class.java)
                intent.putExtra("email", enteredEmail)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Email not recognized!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
