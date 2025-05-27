package com.app.noteapp.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.noteapp.R
import com.app.noteapp.databinding.ItemNoteBinding

class ItemNoteActivity : AppCompatActivity() {
    private lateinit var binding: ItemNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}