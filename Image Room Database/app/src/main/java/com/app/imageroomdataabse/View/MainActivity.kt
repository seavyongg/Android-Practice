package com.app.imageroomdataabse.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.imageroomdataabse.Adapter.ImageAdapter
import com.app.imageroomdataabse.R

import com.app.imageroomdataabse.ViewModel.ImageViewModel
import com.app.imageroomdataabse.databinding.ActivityAddImageBinding
import com.app.imageroomdataabse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var imageViewModel: ImageViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var imageAdapter: ImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        imageViewModel = ViewModelProvider(this)[ImageViewModel::class.java]

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        imageAdapter = ImageAdapter(this)
        binding.recyclerView.adapter = imageAdapter
        imageViewModel.getAllImages().observe(this) {
            imageAdapter.setImageList(it)
        }

        binding.floatAdd.setOnClickListener {
            val intent = Intent(this, AddImageActivity::class.java)
            startActivity(intent)
        }
        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
               TODO("Not yet implemented")

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               imageViewModel.delete(imageAdapter.returnItemAtGivenPosition(viewHolder.adapterPosition))
               



            }
        }).attachToRecyclerView(binding.recyclerView)

    }
}