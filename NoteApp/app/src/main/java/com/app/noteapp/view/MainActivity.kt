package com.app.noteapp.view

import android.app.LauncherActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.noteapp.databinding.ActivityMainBinding
import com.app.noteapp.view.adapter.NoteAdapter
import com.app.noteapp.view.viewModel.NoteViewModel
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel
    lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        noteAdapter = NoteAdapter(this)
        noteViewModel.getAllNotes().observe(this){
            noteAdapter.setNoteList(it)
        }
        binding.notesRecyclerView.apply{
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }
        binding.fabAddNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(noteAdapter.returnItemAtGivenPosition(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(binding.notesRecyclerView)

    }

}