package com.app.noteapp.view.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.noteapp.view.model.Note

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)
    @Update
    suspend fun update(note: Note)
    @Delete
    suspend fun delete(note: Note)
    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    suspend fun insertAll(notes: List<Note>)

    @Query("SELECT * FROM note_table WHERE id = :id")
    fun getItemById(id: Int): Note
}