package com.app.retrofit_practice

import CategoryItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.retrofit_practice.databinding.ItemCategoryBinding

class CategoryAdapter(val list: List<CategoryItem>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){

    //create inner class ViewHolder that extends RecyclerView.ViewHolder to hold the views for each item
    inner class ViewHolder(binding: ItemCategoryBinding ): RecyclerView.ViewHolder(binding.root){
        val binding = ItemCategoryBinding.bind(itemView)
        val name = binding.txtName
        val desc = binding.txtDescription
        val icon = binding.imgIcon

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = ItemCategoryBinding.inflate(  // Inflate the layout for each item
              LayoutInflater.from(parent.context),
              parent,
              false
       )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    //bind data to the view holder and set the values
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position] // Get the current item
        holder.name.text = item.name
        holder.desc.text = item.description ?: "No description"
        holder.icon.load(item.icon) {
            crossfade(true) //crossfade animation when loading images
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
        }
    }

}

