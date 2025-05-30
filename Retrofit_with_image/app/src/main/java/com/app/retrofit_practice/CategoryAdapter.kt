package com.app.retrofit_practice

import CategoryItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.retrofit_practice.databinding.ItemCategoryBinding

class CategoryAdapter(val list: List<CategoryItem>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(){
    inner class ViewHolder(binding: ItemCategoryBinding ): RecyclerView.ViewHolder(binding.root){
        val binding = ItemCategoryBinding.bind(itemView)
        val name = binding.txtName
        val desc = binding.txtDescription
        val icon = binding.imgIcon

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = ItemCategoryBinding.inflate(
              LayoutInflater.from(parent.context),
              parent,
              false
       )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.name
        holder.desc.text = item.description ?: "No description"
        holder.icon.load(item.icon) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
        }
    }

}

