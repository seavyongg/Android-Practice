package com.app.imageroomdataabse.Repository

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.app.imageroomdataabse.Model.Images
import com.app.imageroomdataabse.Room.ImageDao
import com.app.imageroomdataabse.Room.ImageDatabase

class ImageRepository(application: Application) {
    var imageDao: ImageDao? = null
    var imageList : LiveData<List<Images>>? = null
    init{
      val database = ImageDatabase.getDataseInstance(application)
        imageDao = database.imageDao()
        imageList = imageDao?.getAllImages()
    }

    @WorkerThread
    suspend fun insert(images: Images){
        imageDao?.insert(images)
    }
    @WorkerThread
    suspend fun update(images: Images){
        imageDao?.update(images)
    }
    @WorkerThread
    suspend fun delete(images: Images){
        imageDao?.delete(images)
    }
    fun getAllImages() : LiveData<List<Images>>? {
        return imageList
    }
    suspend fun getItemById(id: Int): Images? {
        return imageDao?.getItemById(id)
    }
}