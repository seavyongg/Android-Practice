package com.app.afinal.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.afinal.R
import com.app.afinal.adapter.CartAdapter
import com.app.afinal.data.CartData
import com.app.afinal.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    private var adapter : CartAdapter? = null
    private var total = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.toolbar.title = "Cart"
        // Adapter setup
        adapter = CartAdapter().apply {
            onQuantityChanged = {
                updateTotalPrice()
            }
        }
        binding.toolbar.menu.findItem(R.id.delete).setOnMenuItemClickListener {
            //show toast when item not selected
            if (CartData.cartItems.none { it.isSelected }) {
                Toast.makeText(this, "Please select at least one item to delete.", Toast.LENGTH_SHORT).show()
                false // Do not handle delete action
            }else{
                alertDialog("Delete cart", "Are you sure to delete selected cart items?")
                true // Handle delete action
            }
        }
        binding.rvCartItems.layoutManager = LinearLayoutManager(this)
        binding.rvCartItems.adapter = adapter
        adapter?.submitList(CartData.cartItems)
        binding.btnCheckout.setOnClickListener {
            checkout()
        }
    }


    fun updateTotalPrice(){
        total = CartData.cartItems
            .filter { it.isSelected}
            .sumOf { it.price * it.quantity }
        binding.txtAmountTotal.text = "$ ${total}"
        binding.cvCheckout.visibility = if (total > 0) View.VISIBLE else View.GONE //handle visibility of checkout
        Log.d("Cart", "Total price updated: $total, price: " )

    }
    fun deleteSelectedItems() {
        val newList = CartData.cartItems.filter { !it.isSelected }
        CartData.clear()
        CartData.addAll(newList)
        adapter?.submitList(newList.toList()) //submit new copy of the list to the adapter to trigger UI update
        updateTotalPrice()
    }
    fun alertDialog(title: String, message: String){
        val dialog = AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                deleteSelectedItems()
                updateTotalPrice()
                Toast.makeText(this, "Selected cart was deleted.", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }
    fun checkout(){
        val intent = Intent(this, CheckoutActivity::class.java)
        startActivity(intent)
        CartData.clear()
    }
}