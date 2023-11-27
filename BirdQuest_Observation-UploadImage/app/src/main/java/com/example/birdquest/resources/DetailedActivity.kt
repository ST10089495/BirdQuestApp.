package com.example.birdquest.resources

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.birdquest.R

class DetailedActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Resources"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        val headingB : TextView = findViewById(R.id.tv2)
        val mainTips : TextView = findViewById(R.id.tv3)
        val imageTips : ImageView = findViewById(R.id.iv2)

        val bundle : Bundle? = intent.extras
        val heading = bundle!!.getString("heading")
        val imageId = bundle.getInt("imageId")
        val tips = bundle.getString("tips")

        headingB.text = heading
        mainTips.text = tips
        imageTips.setImageResource(imageId)

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}