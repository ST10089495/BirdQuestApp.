package com.example.birdquest.resources

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.birdquest.R
import com.google.android.material.imageview.ShapeableImageView

class BirdInfoAdapter(private val birdinfoList:ArrayList<BirdInfo>)
    : RecyclerView.Adapter<BirdInfoAdapter.BirdViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BirdViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.resource_items,
        parent,false)

        return BirdViewHolder(itemView, mListener)
    }
    override fun onBindViewHolder(holder: BirdViewHolder, position: Int) {
        val currentItem = birdinfoList[position]
        holder.titleImage.setImageResource(currentItem.image)
        holder.heading.text = currentItem.heading

    }
    override fun getItemCount(): Int {
        return birdinfoList.size
    }
    class BirdViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val titleImage : ImageView = itemView.findViewById(R.id.iv1)
        val heading : TextView = itemView.findViewById(R.id.tv1)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}