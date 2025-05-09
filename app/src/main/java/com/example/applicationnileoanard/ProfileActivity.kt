package com.example.applicationnileoanard

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class ProfileActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private var isEditing = false  // Track edit mode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.profile_page)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        val editProfileBtn = findViewById<MaterialButton>(R.id.btn_edit_profile)
        val saveChangesBtn = findViewById<MaterialButton>(R.id.btn_save_changes)
        val firstName = findViewById<EditText>(R.id.first_name)
        val lastName = findViewById<EditText>(R.id.last_name)
        val username = findViewById<EditText>(R.id.username1)
        val email = findViewById<EditText>(R.id.email)
        val phoneNumber = findViewById<EditText>(R.id.phone_number)
        val profileName = findViewById<TextView>(R.id.profile_name)

        // Load saved user data
        loadUserData(firstName, lastName, username, email, phoneNumber, profileName)

        // Initially disable input fields & hide Save button
        setEditTextsEnabled(false, firstName, lastName, username, email, phoneNumber)
        saveChangesBtn.visibility = View.GONE

        // Toggle edit mode when clicking "Edit Profile"
        editProfileBtn.setOnClickListener {
            isEditing = !isEditing
            setEditTextsEnabled(isEditing, firstName, lastName, username, email, phoneNumber)

            if (isEditing) {
                editProfileBtn.text = "Cancel"
                fadeInButton(saveChangesBtn)
            } else {
                editProfileBtn.text = "Edit Profile"
                fadeOutButton(saveChangesBtn)
            }
        }

        // Save changes when clicking "Save Changes"
        saveChangesBtn.setOnClickListener {
            saveUserData(firstName, lastName, username, email, phoneNumber, profileName)

            isEditing = false
            setEditTextsEnabled(false, firstName, lastName, username, email, phoneNumber)
            editProfileBtn.text = "Edit Profile"
            fadeOutButton(saveChangesBtn)

            Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show()
        }

        // Back button to return to LandingActivity
        val backBtn = findViewById<ImageButton>(R.id.btn_back)
        backBtn.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fadeInButton(button: Button) {
        button.visibility = View.VISIBLE
        button.alpha = 0f
        ObjectAnimator.ofFloat(button, "alpha", 0f, 1f).apply {
            duration = 300  // Smooth fade-in effect
            start()
        }
    }

    private fun fadeOutButton(button: Button) {
        ObjectAnimator.ofFloat(button, "alpha", 1f, 0f).apply {
            duration = 300  // Smooth fade-out effect
            start()
        }
        button.postDelayed({ button.visibility = View.GONE }, 300) // Hide after animation
    }

    private fun setEditTextsEnabled(enabled: Boolean, vararg editTexts: EditText) {
        for (editText in editTexts) {
            editText.isEnabled = enabled
        }
    }

    private fun saveUserData(
        firstName: EditText, lastName: EditText, username: EditText,
        email: EditText, phoneNumber: EditText, profileName: TextView
    ) {
        val editor = sharedPreferences.edit()
        editor.putString("FirstName", firstName.text.toString())
        editor.putString("LastName", lastName.text.toString())
        editor.putString("Username", username.text.toString())
        editor.putString("Email", email.text.toString())
        editor.putString("PhoneNumber", phoneNumber.text.toString())
        editor.apply()

        // Update Profile Name TextView after saving
        profileName.text = "${firstName.text} ${lastName.text}"
    }

    private fun loadUserData(
        firstName: EditText, lastName: EditText, username: EditText,
        email: EditText, phoneNumber: EditText, profileName: TextView
    ) {
        val first = sharedPreferences.getString("FirstName", "") ?: ""
        val last = sharedPreferences.getString("LastName", "") ?: ""

        firstName.setText(first)
        lastName.setText(last)
        username.setText(sharedPreferences.getString("Username", ""))
        email.setText(sharedPreferences.getString("Email", ""))
        phoneNumber.setText(sharedPreferences.getString("PhoneNumber", ""))

        // Set profile name when loading user data
        profileName.text = "$first $last"
    }
}
