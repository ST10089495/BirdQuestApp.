package com.example.birdquest.menu

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.birdquest.R
import com.example.birdquest.resources.BirdInfo
import com.example.birdquest.resources.BirdInfoAdapter
import com.example.birdquest.resources.DetailedActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class  ResourcesActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArray: ArrayList<BirdInfo>
    lateinit var imageId : Array<Int>
    lateinit var heading : Array<String>
    lateinit var tips : Array<String>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Resources"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        imageId = arrayOf(
            R.drawable.resource_img,
            R.drawable.resource_img1,
            R.drawable.resource_img2,
            R.drawable.resource_img3
        )
        heading = arrayOf(
            "List of Birds ",
            "Feeding Birds",
            "Bird Songs",
            "Bird Watching Tips "
        )
        tips = arrayOf(
            getString(R.string.south_african_birds),
            getString(R.string.bird_feeding_tips),
            getString(R.string.bid_songs_tips),
            getString(R.string.bird_watching_tips)
        )
        newRecyclerView=findViewById(R.id.rv)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArray = arrayListOf<BirdInfo>()
        getUserData()

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setSelectedItemId(R.id.action_resources);

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
    private fun getUserData()
    {
        for(i in imageId.indices){
            val birds = BirdInfo(imageId[i],heading[i])
            newArray.add(birds)
        }

        var adapter = BirdInfoAdapter(newArray)
        newRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : BirdInfoAdapter.onItemClickListener{

            override fun onItemClick(position: Int) {
                //Toast.makeText(this@ResourcesActivity,"You Clicked on item no.$position",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@ResourcesActivity,DetailedActivity::class.java)
                intent.putExtra("heading",newArray[position].heading)
                intent.putExtra("imageId",newArray[position].image)
                intent.putExtra("tips",tips[position])
                startActivity(intent)


            }

        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

