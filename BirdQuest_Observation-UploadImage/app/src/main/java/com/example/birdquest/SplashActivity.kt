package com.example.birdquest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.example.birdquest.account.SignUpActivity

class SplashActivity : AppCompatActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_splash)

            val backgroundImg : ImageView = findViewById(R.id.iv_logo)
            val sideAnimation = AnimationUtils.loadAnimation(this,R.anim.slide)
            backgroundImg.startAnimation(sideAnimation)

            Handler().postDelayed({
                startActivity(Intent(this,WelcomeActivity::class.java))
                finish()
            },3000)
        }
    }
