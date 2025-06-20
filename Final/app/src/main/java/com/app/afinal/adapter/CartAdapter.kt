package com.app.afinal.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.app.afinal.databinding.ItemCartBinding
import com.app.afinal.model.Product
import com.app.afinal.viewHolder.CartViewHolder

class CartAdapter : ListAdapter<Product, CartViewHolder>(CartItemDiffCallback()) {
    var onQuantityChanged: (() -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(layoutInflater, parent, false)
        return CartViewHolder(binding,
            onQuantityChanged = { product ->
                Log.d("CartAdapter", "Product clicked: ${product.name}")
                updateProduct(product)
                onQuantityChanged?.invoke()
            }
        )
    }
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun updateProduct(updateProduct: Product){
        val updateList = currentList.toMutableList()
        val index = updateList.indexOfFirst { it.id == updateProduct.id }
        if(index != -1){
            updateList[index] = updateProduct
            submitList(updateList)
        }
    }

}
class CartItemDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }