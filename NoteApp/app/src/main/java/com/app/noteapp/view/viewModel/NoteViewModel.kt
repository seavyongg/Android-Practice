package com.app.noteapp.view.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.room.util.appendPlaceholders
import com.app.noteapp.view.model.Note
import com.app.noteapp.view.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private var repository : NoteRepository
    val noteList : LiveData<List<Note>>

    init{
        repository = NoteRepository(application)
        noteList = repository.getAllNotes()
    }
    fun getItemById(id: Int): Note {
        return repository.getItemById(id)
    }

    fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }
    fun  delete(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }
    fun getAllNotes(): LiveData<List<Note>> {
        return noteList
    }


}
