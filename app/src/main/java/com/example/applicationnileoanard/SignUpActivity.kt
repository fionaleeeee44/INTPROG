package com.example.applicationnileoanard

import android.animation.ObjectAnimator
import android.animation.AnimatorSet
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        val usernameInput = findViewById<EditText>(R.id.username_input)
        val emailInput = findViewById<EditText>(R.id.email_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val confirmPasswordInput = findViewById<EditText>(R.id.confpassword_input)
        val signUpButton = findViewById<Button>(R.id.signup_button)
        val signInText = findViewById<TextView>(R.id.sign_in)

        // List of all views to animate
        val inputLayouts = listOf(
            findViewById<LinearLayout>(R.id.username_input_layout),
            findViewById<LinearLayout>(R.id.email_input_layout),
            findViewById<LinearLayout>(R.id.password_input_layout),
            findViewById<LinearLayout>(R.id.confpassword_input_layout)
        )

        val scaleBounceAnim = AnimationUtils.loadAnimation(this, R.anim.scale_bounce)
        val viewsToPopUp = listOf(signUpButton, signInText)
        signUpButton.startAnimation(scaleBounceAnim)

        // Top to Bottom animation for TextInputLayouts
        for ((index, view) in inputLayouts.withIndex()) {
            val animation = ObjectAnimator.ofFloat(view, "translationY", -500f, 0f)
            animation.duration = 500
            animation.startDelay = (index * 100).toLong() // Delay each animation
            animation.interpolator = AccelerateDecelerateInterpolator()
            animation.start()
        }

        // Pop-up animation for SignUp button and SignIn Text
        val animatorSet = AnimatorSet()
        val popUpAnimations = viewsToPopUp.map {
            ObjectAnimator.ofFloat(it, "alpha", 0f, 1f).apply {
                duration = 500
                interpolator = AccelerateDecelerateInterpolator()
            }
        }
        animatorSet.playTogether(popUpAnimations)
        animatorSet.start()

        signUpButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = confirmPasswordInput.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save user info to SharedPreferences
            val sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putString("user_username", username)
                putString("user_email", email)
                putString("user_password", password) // In production, use hashed passwords!
                apply()
            }

            Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()

            // Redirect to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        signInText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
