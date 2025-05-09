package com.example.applicationnileoanard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Views
        val usernameInput = findViewById<EditText>(R.id.username_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val loginButton = findViewById<Button>(R.id.login_button)
        val signUpText = findViewById<TextView>(R.id.sign_up)
        val forgot = findViewById<TextView>(R.id.forgot_password)
        val loginCard = findViewById<CardView>(R.id.login_card) // Make sure you add android:id="@+id/login_card" to your CardView in XML

        // Load animations
        val slideUpAnim = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        val scaleBounceAnim = AnimationUtils.loadAnimation(this, R.anim.scale_bounce)

        // Apply animations on start
        loginCard?.startAnimation(fadeInAnim)
        usernameInput.startAnimation(slideUpAnim)
        passwordInput.startAnimation(slideUpAnim)
        loginButton.startAnimation(fadeInAnim)
        forgot.startAnimation(fadeInAnim)
        signUpText.startAnimation(fadeInAnim)

        // SharedPreferences
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        loginButton.setOnClickListener {
            // Animate the button
            loginButton.startAnimation(scaleBounceAnim)

            val enteredUsername = usernameInput.text.toString().trim()
            val enteredPassword = passwordInput.text.toString().trim()
            val savedUsername = sharedPreferences.getString("user_username", null)
            val savedPassword = sharedPreferences.getString("user_password", null)

            if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (savedUsername == null || savedPassword == null) {
                Toast.makeText(this, "No account found! Please register first.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (enteredUsername == savedUsername && enteredPassword == savedPassword) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_SHORT).show()
            }
        }

        forgot.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        signUpText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
}
