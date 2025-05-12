package com.example.applicationnileoanard

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationnileoanard.databinding.LandingPageBinding

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: LandingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Animations
        val fadeSlideIn = AnimationUtils.loadAnimation(this, R.anim.fade_slide_in)
        val bounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in)

        binding.centerContent.startAnimation(fadeSlideIn)
        binding.signinButton.startAnimation(bounce)
        binding.signupButton.startAnimation(bounce)
        binding.logoImage.startAnimation(zoomIn)

        // Button actions
        binding.signinButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}