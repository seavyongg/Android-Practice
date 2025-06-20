package com.app.afinal.viewHolder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.app.afinal.databinding.ItemCartBinding
import com.app.afinal.model.Product
import com.squareup.picasso.Picasso

class CartViewHolder(private val binding : ItemCartBinding, private val onQuantityChanged: (Product)-> Unit) :
 RecyclerView.ViewHolder(binding.root) {
     fun bind(product: Product){
         Picasso.get().load(product.imageUrl).into(binding.imageProduct)
         binding.productName.text = product.name
         binding.productPrice.text = "$ ${product.price}"
         getBindQty(product)
         Log.d("Cart", "Binding product: ${product.name}, Quantity: ${product.quantity}")
         //increase and decrease quantity buttons
         binding.txtDecrease.setOnClickListener {
             if (product.quantity > 1) {
                 product.quantity--
                 onQuantityChanged(product)
                 getBindQty(product)

             }
         }
         binding.txtIncrease.setOnClickListener {
               if(product.quantity < 99) {
                    product.quantity++
                    onQuantityChanged(product)
                    getBindQty(product)
                    Log.d("Cart", "Product quantity increased: ${product.name}, New Quantity: ${product.quantity}")
               }
            }
         // Checkbox for selection
         val checkBox = binding.checkboxSelect
         checkBox.isChecked = product.isSelected
         checkBox.setOnCheckedChangeListener { _, isChecked ->
             product.isSelected = isChecked
             onQuantityChanged(product)
         }
         }
    // Function to update the quantity text views
    fun getBindQty(product: Product) {
        binding.txtquantity.text = "${product.quantity}"
        binding.txtQty.text = "Qty: ${product.quantity}"
    }

     }
