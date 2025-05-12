package com.example.applicationnileoanard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.applicationnileoanard.api.ApiService
import com.example.applicationnileoanard.api.LoginRequest
import com.example.applicationnileoanard.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // Initialize API service
        apiService = RetrofitClient.apiService
        Log.d(TAG, "API Service initialized with base URL: ${RetrofitClient.BASE_URL}")

        // Views
        val usernameInput = findViewById<EditText>(R.id.username_input)
        val passwordInput = findViewById<EditText>(R.id.password_input)
        val loginButton = findViewById<Button>(R.id.login_button)
        val signUpText = findViewById<TextView>(R.id.sign_up)
        val forgot = findViewById<TextView>(R.id.forgot_password)
        val loginCard = findViewById<CardView>(R.id.login_card)

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

        loginButton.setOnClickListener {
            // Animate the button
            loginButton.startAnimation(scaleBounceAnim)

            val email = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Show loading indicator
            loginButton.isEnabled = false
            loginButton.text = "Logging in..."

            // Make API call
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    Log.d(TAG, "Making login API call...")
                    val request = LoginRequest(email, password)
                    Log.d(TAG, "Request body: $request")
                    
                    val response = apiService.login(request)
                    Log.d(TAG, "Response code: ${response.code()}")
                    Log.d(TAG, "Response body: ${response.body()}")
                    Log.d(TAG, "Response error body: ${response.errorBody()?.string()}")

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            Log.i(TAG, "Login successful")
                            Toast.makeText(this@LoginActivity, "Login Successful!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                            finish()
                        } else {
                            val errorMessage = "Login failed: ${response.code()} - ${response.message()}"
                            Log.e(TAG, errorMessage)
                            Toast.makeText(this@LoginActivity, "Invalid email or password!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Login error", e)
                    withContext(Dispatchers.Main) {
                        val errorMessage = "Error: ${e.message}"
                        Log.e(TAG, errorMessage)
                        Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                } finally {
                    withContext(Dispatchers.Main) {
                        loginButton.isEnabled = true
                        loginButton.text = "Sign In"
                    }
                }
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
