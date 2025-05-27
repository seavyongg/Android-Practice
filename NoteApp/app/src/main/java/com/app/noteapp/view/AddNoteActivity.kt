package com.app.noteapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.app.noteapp.R
import com.app.noteapp.databinding.ActivityAddNoteBinding
import com.app.noteapp.view.model.Note
import com.app.noteapp.view.viewModel.NoteViewModel

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    lateinit var noteViewModel : NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //back navigation
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        binding.toolbarContent.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_save-> {
                    saveNote()
                    true

                }
                else -> {
                Toast.makeText(this, "Unknown action", Toast.LENGTH_SHORT).show()
                    false
                }
            }
        }



    }
    private fun saveNote(){
        val title = binding.txtTitle.text.toString()
        val description = binding.txtDescription.text.toString()
        if(title.isEmpty()){
            binding.txtTitle.error = "Title cannot be empty"
            return
        }else{
            val note = Note(
                title = title,
                description = description
            )
            noteViewModel.insert(note)
            Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show()
            finish()

        }

    }

    }


