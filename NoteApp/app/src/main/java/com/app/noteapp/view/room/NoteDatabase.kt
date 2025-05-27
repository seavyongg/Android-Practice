package com.app.noteapp.view.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.noteapp.view.model.Note
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Note::class],
    version = 1,
)
abstract  class NoteDatabase:RoomDatabase() {



    abstract fun noteDao(): NoteDao
    companion object {
        @Volatile
        private var INSTANCE: NoteDatabase? = null

        // Singleton pattern to create the database instance
        // and avoid multiple instances of the database at the same time
        //use CoroutineScope to perform the operations in the background, It is working with the UI thread
        fun getDatabaseInstance(context: Context, scope: CoroutineScope): NoteDatabase {
            return INSTANCE ?: synchronized(this) { //if the instance is null then create the instance
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).addCallback(NoteDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    private class NoteDatabaseCallback( private val scope: CoroutineScope): RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let {
                    database ->
                scope.launch {
                    val noteDAO = database.noteDao()

                    noteDAO.insert(Note("Title 1", "Description 1"))
                    noteDAO.insert(Note("Title 2", "Description 2"))
                    noteDAO.insert(Note("Title 3", "Description 3"))
                }



            }
        }
    }


}