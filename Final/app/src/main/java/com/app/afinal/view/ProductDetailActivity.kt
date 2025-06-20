package com.app.afinal.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.app.afinal.R
import com.app.afinal.api.ApiClient
import com.app.afinal.api.ProductService
import com.app.afinal.data.CartData
import com.app.afinal.databinding.ActivityProductDetailBinding
import com.app.afinal.databinding.ActivityProductListBinding
import com.app.afinal.model.Product
import com.app.afinal.view.ProductListActivity.Companion.PRODUCT_ID_KEY
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlin.math.log

class ProductDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        binding.toolbar.setNavigationOnClickListener() {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.toolbar.menu.findItem(R.id.icCart).intent = Intent(this, CartActivity::class.java)
        setContentView(binding.root)
        val productId = intent.getStringExtra(PRODUCT_ID_KEY)
        Log.d("ProductDetailActivity", "Received product ID: $productId")
        if (productId != null) {
            binding.progressBar.visibility = View.GONE
            loadProductById(productId.toInt()) // Convert productId to Int
        } else {
            Log.d("ProductDetailActivity", "No product ID provided")
        }

    }
    private fun loadProductById(productId: Int): Product? {
        lifecycleScope.launch {
            try{
                val product = ApiClient.productService.loadProductList().find { it.id == productId }
                if (product != null) {
                    displayProductDetails(product)
                } else {
                    Log.d("ProductDetailActivity", "Product with ID $productId not found")
                }
            }catch (e: Exception) {
                e.printStackTrace()
                Log.d("ProductDetailActivity", "Error loading product details: ${e.message}")
                showDialog("Error", "Failed to load product details.")

            }

        }
        return null // Return null if product not found
    }
    private fun displayProductDetails(product: Product) {
        binding.productName.text = product.name
        binding.productPrice.text = "$ ${product.price}"
        Picasso.get()
            .load(product.imageUrl)
            .into(binding.imagePhone)
        binding.txtOs.text = "OS: ${product.specs.os}" ?: "N/A"
        binding.txtCpu.text = "Cpu: ${product.specs.cpu}" ?: "N/A"
        binding.txtMemory.text = "Memory: ${product.specs.memory}" ?: "N/A"
        binding.txtSceensize.text = "ScreenSize: ${product.specs.screenSize}" ?: "N/A"

        binding.txtAddToCart.setOnClickListener{
            addToCart(product)
        }
    }
    fun addToCart(product: Product) {
        CartData.addToCart(product)
        Log.d("ProductDetailActivity", "Product added to cart: ${product.name}, Quantity: ${product.quantity}")
        Toast.makeText(this, "${product.name} added to cart", Toast.LENGTH_SHORT).show()

    }
}