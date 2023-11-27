package com.example.birdquest.menu

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import com.example.birdquest.AdapterObservation
import com.example.birdquest.AddObservationActivity
import com.example.birdquest.ModelObservation
import com.example.birdquest.R
import com.example.birdquest.databinding.ActivityListBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListActivity : AppCompatActivity() {
    //view binding
    private  lateinit var  binding: ActivityListBinding
    //firebase auth
    private lateinit var firebaseAuth: FirebaseAuth

    //arraylist to hold observations
    private lateinit var observationArrayList: ArrayList<ModelObservation>
    //adapter
    private lateinit var adapterObservation: AdapterObservation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        loadObservations()

        //search
        binding.searchEt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //called as and when user type anything
                try {
                    adapterObservation.filter.filter(s)
                }
                catch (e: Exception){

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })


        //handle click, start add observation page
        val addObservationButton = findViewById<Button>(R.id.addObservationButton)
        addObservationButton.setOnClickListener {
            startActivity(Intent(this, AddObservationActivity::class.java))
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setSelectedItemId(R.id.action_list);

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

    private fun loadObservations() {
        // init arrayList
        observationArrayList = ArrayList( )

        //get all observations from firebase database... Firebase DB> Observations
        val ref = FirebaseDatabase.getInstance().getReference("Observations")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before start adding data into it
                observationArrayList.clear()
                for(ds in snapshot.children){
                    val model = ds.getValue(ModelObservation::class.java)

                    //add to arrayList
                    observationArrayList.add(model!!)
                }
                //setup adapter
                adapterObservation = AdapterObservation(this@ListActivity,observationArrayList)
                //set adapter to recyclerview
                binding.observationsRv.adapter = adapterObservation
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}