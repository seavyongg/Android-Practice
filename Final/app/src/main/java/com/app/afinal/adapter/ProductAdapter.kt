package com.app.afinal.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.app.afinal.databinding.ItemProductBinding
import com.app.afinal.model.Product
import com.app.afinal.view.ProductListActivity
import com.app.afinal.viewHolder.ProductViewHolder

class ProductAdapter(private val allData: List<Product>): ListAdapter<Product, ProductViewHolder>(ProductItemDiffCallBack()) {
    var onItemClickListener: ((position: Int)-> Unit)? = null //unit used to represent a function that does not return any value

    //contructor
    init {
        submitList(allData) //submit the list to the adapter
    }

    //given data set, create a new view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding){
            product: Product -> onItemClickListener?.invoke(allData.indexOf(product)) //invoke the listener when item is clicked
            Log.d("ProductAdapter", "Product: ${product.name} clicked")
        }
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{
            onItemClickListener?.invoke(position) //invoke the listener when item is clicked
            Log.d("Product", "Prodcut: ${getItem(position).name} clicked at position $position")
        }
    }
    fun filterByName(keyword: String): List<Product> {
        val newList = allData.filter { product -> product.name.contains(keyword, true)  }
        Log.d("ProductAdapter", "Filter by name: $newList")
        submitList(newList)
        return newList
    }


}
class ProductItemDiffCallBack: DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}