package com.example.birdquest

import android.widget.Filter

class FilterObservation: Filter {

    //arraylist in which we want to search
    private var filterList:ArrayList<ModelObservation>

    //adapter in which filter need to be implemented
    private var adapterObservation: AdapterObservation

    constructor(
        filterList: ArrayList<ModelObservation>,
        adapterObservation: AdapterObservation
    ) : super() {
        this.filterList = filterList
        this.adapterObservation = adapterObservation
    }

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var constraint = constraint
        val results = FilterResults()

        //value should not be null and not empty

        if (constraint != null && constraint.isNotEmpty()){
            //search value is nor null not empty

            //change to uppercase, or lowercase to avoid case sensitivity
            constraint = constraint.toString().uppercase()
            val filteredModels:ArrayList<ModelObservation> = ArrayList()
            for (i in 0 until filterList.size){
                //validate
                if(filterList[i].birdName.uppercase().contains(constraint)){
                    //add to filtered list
                    filteredModels.add(filterList[i])
                }
            }
            results.count = filteredModels.size
            results.values = filteredModels
        }
        else{
            //search value is either null or empty
            results.count = filterList.size
            results.values = filterList
        }
        return results //don't miss it

    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults) {
        //apply filter changes
        adapterObservation.observationArrayList = results.values as ArrayList<ModelObservation>

        //notify changes
        adapterObservation.notifyDataSetChanged()
    }
}