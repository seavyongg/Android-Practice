package com.app.imageroomdataabse.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.imageroomdataabse.Model.Images

@Dao
interface ImageDao {

    @Insert
    suspend fun insert(images: Images)

    @Update
    suspend fun update(images: Images)

    @Delete
    suspend fun delete(images: Images)

    @Query("SELECT * FROM image_table ORDER BY imageId ASC")
    fun getAllImages() : LiveData<List<Images>>

    @Query("SELECT * FROM image_table WHERE imageId = :id")
    suspend fun getItemById(id: Int): Images


}