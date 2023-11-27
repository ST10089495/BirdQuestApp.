package com.example.birdquest.account

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.birdquest.R
import com.example.birdquest.databinding.ActivitySettingsBinding
import com.example.birdquest.menu.HomeActivity
import com.example.birdquest.menu.ListActivity
import com.example.birdquest.menu.ProfileActivity
import com.example.birdquest.menu.ResourcesActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var toggleSystem: Switch
    private lateinit var distanceInput: EditText
    private lateinit var saveButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_settings)
        setContentView(binding.root)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Settings"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)


        toggleSystem = findViewById(R.id.toggleSystem)
        distanceInput = findViewById(R.id.distanceInput)
        saveButton = findViewById(R.id.saveButton)

        // Load and display saved user preferences
        loadUserPreferences()

        toggleSystem.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Metric system is selected
                distanceInput.hint = "Max Distance (Kilometers)"
            } else {
                // Imperial system is selected
                distanceInput.hint = "Max Distance (Miles)"
            }
        }

        // Save button click listener
        saveButton.setOnClickListener {
            saveUserPreferences()
            finish() // Close the settings activity
        }
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setSelectedItemId(R.id.action_profile)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.action_resources -> startActivity(Intent(this, ResourcesActivity::class.java))
                R.id.action_list -> startActivity(Intent(this, ListActivity::class.java))
                R.id.action_profile -> startActivity(Intent(this, ProfileActivity::class.java))
            }
            true
        }
    }

    private fun loadUserPreferences() {
        // Load user preferences from a data store (e.g., SharedPreferences) and update UI elements
        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val useMetricSystem = sharedPreferences.getBoolean("useMetricSystem", true)
        val useImperialSystem = sharedPreferences.getBoolean("useImperialSystem", true)
        val maxDistance = sharedPreferences.getInt("maxDistance", 100)

        toggleSystem.isChecked = useMetricSystem

        if (useMetricSystem) {
            distanceInput.hint = "Max Distance (Kilometers)"
        } else {
            distanceInput.hint = "Max Distance (Miles)"
            // Convert distance from kilometers to miles
            val milesDistance = convertKilometersToMiles(maxDistance)
            distanceInput.setText(milesDistance.toString())
        }
    }

    private fun convertKilometersToMiles(kilometers: Int): Int {
        // Conversion ratio: 1 km = 0.621371 miles
        return (kilometers * 0.621371).toInt()
    }
    private fun convertMilesToKilometers(miles: Int): Int {
        // Conversion ratio: 1 mile = 1.60934 kilometers
        return (miles * 1.60934).toInt()
    }

    // Inside saveUserPreferences()
    private fun saveUserPreferences() {
        val maxDistanceStr = distanceInput.text.toString()
        if (maxDistanceStr.isNotEmpty() && maxDistanceStr.toIntOrNull() != null) {
            val maxDistance = maxDistanceStr.toInt()

            // Get the current user
            val user = FirebaseAuth.getInstance().currentUser

            if (user != null) {
                val userId = user.uid

                val database = FirebaseDatabase.getInstance()
                val userSettingsRef = database.getReference("users").child(userId).child("settings")

                val useMetricSystem = toggleSystem.isChecked

                userSettingsRef.child("useMetricSystem").setValue(useMetricSystem)
                userSettingsRef.child("maxDistance").setValue(maxDistance)

                showFeedback("Preferences saved!") // Display a success message
            } else {
                showFeedback("User not signed in!") // Notify the user to sign in
            }
        } else {
            showFeedback("Invalid distance!") // Display an error message
        }
    }


    private fun showFeedback(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}