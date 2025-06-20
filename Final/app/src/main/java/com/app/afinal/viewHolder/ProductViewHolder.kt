package com.app.afinal.viewHolder

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.afinal.databinding.ItemProductBinding
import com.app.afinal.model.Product
import com.app.afinal.view.ProductListActivity
import com.squareup.picasso.Picasso

class ProductViewHolder(private val binding: ItemProductBinding, private val onItemClick:(Product)-> Unit): ViewHolder(binding.root) {
    fun bind(product: Product){ //bind data to view
        Picasso.get()
            .load(product.imageUrl)
            .into(binding.imgProduct)
        binding.txtProductName.text = product.name
        binding.txtProductPrice.text = "$"+ product.price
        binding.root.setOnClickListener {
            onItemClick(product)
        }
    }


}