package com.app.noteapp.view.repository

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.noteapp.view.model.Note
import com.app.noteapp.view.room.NoteDao
import com.app.noteapp.view.room.NoteDatabase
import com.app.noteapp.view.viewModel.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class NoteRepository(application: Application) {

    var noteDao : NoteDao
    var noteList : LiveData<List<Note>>

    init{
        val noteDatabase = NoteDatabase.getDatabaseInstance(application, CoroutineScope(Dispatchers.IO))
        noteDao = noteDatabase.noteDao()
        noteList = noteDao.getAllNotes()
    }

    @WorkerThread
    suspend fun insert(note: Note){
        noteDao.insert(note)
    }
    @WorkerThread
    suspend fun update(note: Note){
        noteDao.update(note)
    }
    @WorkerThread
    suspend fun delete(note: Note){
        noteDao.delete(note)
    }
    fun getAllNotes(): LiveData<List<Note>> {
        return noteList
    }
    fun getItemById(id: Int): Note{
      return noteDao.getItemById(id)
    }
}