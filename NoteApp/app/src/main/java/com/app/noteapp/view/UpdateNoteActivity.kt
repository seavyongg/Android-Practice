package com.app.noteapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.app.noteapp.R
import com.app.noteapp.databinding.ActivityUpdateNoteBinding
import com.app.noteapp.view.model.Note
import com.app.noteapp.view.viewModel.NoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNoteBinding
    lateinit var viewModel: NoteViewModel
    var id = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
       binding.toolbarContent.navigationIcon = getDrawable(R.drawable.ic_back)
        binding.toolbarContent.setNavigationOnClickListener {
            finish()
        }
        viewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        getAndSetData()
        binding.toolbarContent.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_save -> {
                    updateNote()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
    fun getAndSetData() {
        id = intent.getIntExtra("id", -1)
        if (id != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                val note = viewModel.getItemById(id)
                withContext(Dispatchers.Main) {
                    binding.titleEditText.setText(note.title)
                    binding.descriptionEditText.setText(note.description)

                }
            }
        }
    }
    private fun updateNote(){
        val title = binding.titleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        val note = Note(
           title,
            description
        )
        note.id = id
        viewModel.update(note)
        finish()

    }
}