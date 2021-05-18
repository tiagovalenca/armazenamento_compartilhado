package com.cesar.school.armazenamentocompartilhado

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

internal class StoredImageAdapter(val viewModel: MainViewModel, val imageList: List<StoredImage>) :
    RecyclerView.Adapter<StoredImageAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageName: TextView = view.findViewById(R.id.displayName)
        var imageData: ImageView = view.findViewById(R.id.imageView)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val image = imageList[position]
        holder.imageName.text = image.displayName
        holder.imageData.setImageURI(image.contentUri)
    }
    override fun getItemCount(): Int {
        return imageList.size
    }
}