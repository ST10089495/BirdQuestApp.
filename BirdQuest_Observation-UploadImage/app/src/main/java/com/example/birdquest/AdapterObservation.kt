package com.example.birdquest

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.birdquest.databinding.RowObservationBinding
import com.google.firebase.database.FirebaseDatabase

class AdapterObservation: RecyclerView.Adapter<AdapterObservation.HolderObservation>,Filterable{

    private val context: Context
    public var observationArrayList: ArrayList<ModelObservation>
    private var filterList: ArrayList<ModelObservation>

    private var filter: FilterObservation? = null

    private lateinit var binding: RowObservationBinding

    //constructor
    constructor(context: Context, observationArrayList: ArrayList<ModelObservation>) {
        this.context = context
        this.observationArrayList = observationArrayList
        this.filterList = observationArrayList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderObservation {
        //inflate/bind row_category.xml
        binding = RowObservationBinding.inflate(LayoutInflater.from(    context), parent, false)

        return HolderObservation(binding.root)
    }

    override fun getItemCount(): Int {
        return observationArrayList.size
    }

    override fun onBindViewHolder(holder: HolderObservation, position: Int) {
        /* Get Data, Set Data, Handle clicks */

        //get data
        val model = observationArrayList[position]
        val id = model.id
        val birdName = model.birdName
        val birdDescription = model.birdDescription
        val uid = model.uid

        //set data
        holder.observationTv.text = birdName

        //handle click, delete observation
        holder.deleteBtn.setOnClickListener{
            //confirm before delete
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
                .setMessage("Are you sure you want to delete observation?")
                .setPositiveButton("Confirm"){a, d->
                    Toast.makeText(context, "Deleting...", Toast.LENGTH_SHORT).show()
                    deleteObservation(model,holder)
                }
                .setNegativeButton("Cancel"){a, d->
                    a.dismiss()
                }
                .show()
        }
    }

    private fun deleteObservation(model: ModelObservation, holder: HolderObservation) {
        //get id of observation to delete
        val id = model.id
        //Firebase DB> Observations > observationId
        val ref = FirebaseDatabase.getInstance().getReference("Observations")
        ref.child(id)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Deleted...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ e->
                Toast.makeText(context, "Unable to delete due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    //ViewHolder class to hold/init UI views for row_observation.xml
    inner class HolderObservation(itemView: View): RecyclerView.ViewHolder(itemView){
        //init ui views
        var observationTv:TextView = binding.observationTv
        var deleteBtn:ImageButton = binding.deleteBtn
    }

    override fun getFilter(): Filter {
        if( filter == null){
            filter = FilterObservation(filterList, this)
        }
        return filter as FilterObservation
    }


}