package com.app.imageroomdataabse.ViewModel

import android.app.Application
import android.media.Image
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.imageroomdataabse.Model.Images
import com.app.imageroomdataabse.Repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageViewModel(application: Application) : AndroidViewModel(application) {
    private var repository : ImageRepository
    val imageList : LiveData<List<Images>>

    init {
        repository = ImageRepository(application)
        imageList = repository.getAllImages()!!
    }
    fun insert(images: Images)  = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(images)
    }
    fun update(images: Images)  = viewModelScope.launch(Dispatchers.IO) {
        repository.update(images)
    }
    fun delete(images: Images)  = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(images)
    }
    fun getAllImages() : LiveData<List<Images>> {
        return imageList
    }
    suspend fun getItemById(id: Int): Images {
        return repository.getItemById(id)!!
    }

}
//class ImageViewModel(application: Application): AndroidViewModel(application){
//    private var repository: ImageRepository
//    val imageList: LiveData<List<Images>>
//    init{
//        repository = ImageRepository(application)
//        imageList = repository.getAllImages()!!
//
//    }
//    fun insert(images: Images) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(images)
//    }
//    fun update(images: Images) = viewModelScope.launch {
//        repository.update(images)
//    }
//    fun delete(images: Images) = viewModelScope.launch {
//        repository.delete(images)
//    }
//    fun getAllImages(): LiveData<List<Images>> {
//        return imageList
//    }
//    suspend fun getItemById(id: Int): Images{
//        return repository.getItemById(id)!!
//    }
//}