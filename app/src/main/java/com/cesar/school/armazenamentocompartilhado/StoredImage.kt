package com.cesar.school.armazenamentocompartilhado

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import java.util.*

data class StoredImage(
    val id: Long,
    val displayName: String,
    val dateAdded: Date,
    val contentUri: Uri
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<StoredImage>(){
            override fun areItemsTheSame(oldItem: StoredImage, newItem: StoredImage) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: StoredImage, newItem: StoredImage) =
                oldItem == newItem
        }
    }
}