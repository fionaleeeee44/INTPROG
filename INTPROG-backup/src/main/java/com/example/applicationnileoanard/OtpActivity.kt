package com.example.applicationnileoanard

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class OtpActivity : AppCompatActivity() {

    private var generatedOtp: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val submit = findViewById<Button>(R.id.btn_verify_otp)
        val resend = findViewById<TextView>(R.id.resend_otp)

        // Generate and "send" OTP
        generateAndSendOtp()

        submit.setOnClickListener {
            val userOtp = getEnteredOtp()
            if (userOtp == generatedOtp) {
                Toast.makeText(this, "OTP Verified Successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ChangePasswordActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid OTP, please try again", Toast.LENGTH_SHORT).show()
            }
        }

        resend.setOnClickListener {
            generateAndSendOtp()
        }
    }

    private fun generateAndSendOtp() {
        generatedOtp = (100000..999999).random().toString()
        Toast.makeText(this, "OTP sent to your email: $generatedOtp", Toast.LENGTH_LONG).show()
    }

    private fun getEnteredOtp(): String {
        val d1 = findViewById<EditText>(R.id.otp_digit_1).text.toString()
        val d2 = findViewById<EditText>(R.id.otp_digit_2).text.toString()
        val d3 = findViewById<EditText>(R.id.otp_digit_3).text.toString()
        val d4 = findViewById<EditText>(R.id.otp_digit_4).text.toString()
        val d5 = findViewById<EditText>(R.id.otp_digit_5).text.toString()
        val d6 = findViewById<EditText>(R.id.otp_digit_6).text.toString()
        return d1 + d2 + d3 + d4 + d5 + d6
    }
}
