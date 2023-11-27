package com.example.birdquest

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.example.birdquest.account.SignInActivity
import com.example.birdquest.databinding.ActivityAddObservationBinding
import com.example.birdquest.menu.HomeActivity
import com.example.birdquest.menu.ListActivity
import com.example.birdquest.menu.ProfileActivity
import com.example.birdquest.menu.ResourcesActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AddObservationActivity : AppCompatActivity() {
    //view binding
    private lateinit var binding:ActivityAddObservationBinding

    //firebase auth
    private lateinit var firebaseAuth:FirebaseAuth

    //progress dialog
    private lateinit var progressDialog: ProgressDialog

    private lateinit var ImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddObservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.selectImage.setOnClickListener{
            selectImage()
        }

        binding
        //initialise //firebase auth
        firebaseAuth= FirebaseAuth.getInstance()

        //config progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click go back button
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        //Handle click, begin upload observation
        binding.submitObsBtn.setOnClickListener{
            validateData()
            uploadImage()
        }

        //Bottom Navigation
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

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading Image ...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(ImageUri)
            .addOnSuccessListener {

                binding.birdImage.setImageURI(null)
            Toast.makeText(this@AddObservationActivity,"Succesfully uploaded",Toast.LENGTH_SHORT).show()
            if(progressDialog.isShowing) progressDialog.dismiss()

        }.addOnFailureListener {
            if (progressDialog.isShowing)progressDialog.dismiss()
            }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "images/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK){

            ImageUri = data?.data!!
            binding.birdImage.setImageURI(ImageUri)

        }
    }

    private var birdName = ""
    private var birdDesc = ""

    private fun validateData(){
        //Validate data method

        //input data
        birdName = binding.birdNameEt.text.toString().trim()
        birdDesc = binding.birdDescEt.text.toString().trim()

        //validate data
        if(birdName.isEmpty()){
            Toast.makeText(this,"Enter bird name...", Toast.LENGTH_SHORT).show()
        }
        else if(birdDesc.isEmpty()){
            Toast.makeText(this,"Enter bird description...", Toast.LENGTH_SHORT).show()
        }
        else{
            addObservationFirebase()
        }

        //set/create data

    }

    private fun addObservationFirebase() {
        //show progress
        progressDialog.setMessage("Creating observation")
        progressDialog.show()


        //get timestamp
        val timestamp = System.currentTimeMillis()

        //setup data to add in firebase db
        val hashMap= HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["birdName"] = birdName
        hashMap["birdDescription"] = birdDesc
        hashMap["uid"]="${firebaseAuth.uid}"

        //add to firebase db: Database Root > Observations> observationId> observation info
        val ref = FirebaseDatabase.getInstance().getReference("Observations")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener{
                progressDialog.dismiss()
                Toast.makeText(this,"Added successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ListActivity::class.java))


            }
            .addOnFailureListener(){ e->
                //Failed to add
                progressDialog.dismiss()
                Toast.makeText(this,"Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()


            }

    }
}