package com.cesar.school.armazenamentocompartilhado

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application): AndroidViewModel(application) {
    var images = MutableLiveData<List<StoredImage>>()
    var tempList = ArrayList<StoredImage>()

    internal fun getImageList(): MutableLiveData<List<StoredImage>> {
        if (images == null) {
            images = MutableLiveData()
        }
        return images as MutableLiveData<List<StoredImage>>
    }

    fun loadImages() {
            tempList = queryImages()
            images.value = tempList
    }

    private fun queryImages(): ArrayList<StoredImage> {
        val imageList = ArrayList<StoredImage>()


        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED
        )

        val selection = "${MediaStore.Images.Media.MIME_TYPE} = ?"

        val selectionArgs = arrayOf(MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg"))

        val sortOrder = "${MediaStore.Images.Media.DISPLAY_NAME} ASC"

        getApplication<Application>().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->

            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dateModifiedColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED)
            val displayNameColumn =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

            while (cursor.moveToNext()) {

                val id = cursor.getLong(idColumn)
                val dateModified =
                    Date(TimeUnit.SECONDS.toMillis(cursor.getLong(dateModifiedColumn)))
                val displayName = cursor.getString(displayNameColumn)

                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val image = StoredImage(id, displayName, dateModified, contentUri)
                imageList.add(image)
            }
        }

        return imageList
    }
}