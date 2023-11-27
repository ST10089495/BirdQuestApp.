package com.example.birdquest.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.birdquest.R
import com.example.birdquest.account.SettingsActivity
import com.example.birdquest.account.SignInActivity
import com.example.birdquest.databinding.ActivityProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var user: FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Profile"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        user = FirebaseAuth.getInstance()
        loadUserInfo()
        binding.signoutbtn.setOnClickListener {
            user.signOut()
            startActivity(
                Intent(this, SignInActivity::class.java )
            )
        }
        val settingsButton = findViewById<Button>(R.id.settingsButton)

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setSelectedItemId(R.id.action_profile);

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> startActivity(Intent(this, HomeActivity::class.java))
                R.id.action_resources -> startActivity(Intent(this, ResourcesActivity::class.java))
                R.id.action_list -> startActivity(Intent(this,ListActivity::class.java))
                R.id.action_profile -> startActivity(Intent(this, ProfileActivity::class.java))
            }
            true
        }
    }
    private fun showToast(str:String){
        Toast.makeText(this,str, Toast.LENGTH_SHORT).show()
    }

    private fun loadUserInfo() {
        val textView = findViewById<TextView>(R.id.TVemail)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val email = currentUser.email
            textView.text = email

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
