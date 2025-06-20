package com.app.afinal.view

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.DialogTitle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Query
import com.app.afinal.R
import com.app.afinal.adapter.ProductAdapter
import com.app.afinal.api.ApiClient
import com.app.afinal.api.ApiClient.productService
import com.app.afinal.api.ProductService
import com.app.afinal.databinding.ActivityHomeBinding
import com.app.afinal.model.Product
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private var adapter: ProductAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        Thread.sleep(1000) // Simulate a delay for 1 second
        installSplashScreen() // Install the splash screen because it is required for the first activity
        setContentView(binding.root)

        //navigate to all products
        binding.icMoreProduct.setOnClickListener {
          val intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
        }

        //cart
        binding.icCart.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        loadProducts()
        searchProduct()


    }

    fun loadProducts(){

        lifecycleScope.launch {
            try{
                val productList = ApiClient.productService.loadProductList()
                displayProducts(productList)
            }catch (e: Exception) {
                Log.d("HomeActivity", "Error loading product list: ${e.message}")
                showDialog(
                    title = "Error",
                    message = "Failed to load products. Please try again later.",
                )

            }
        }

    }
    fun displayProducts(productList: List<Product>){
        adapter = ProductAdapter(productList)
        adapter?.onItemClickListener = {
            position ->
            val product = productList[position]
            Log.d("Product", "Selected product: ${product.name}")
        }
        binding.rvProductList.adapter = adapter
        binding.rvProductList.layoutManager = GridLayoutManager(this, 2)
        //given display only 4 products
        if (productList.size > 4) {
            adapter?.submitList(productList.subList(0, 4))
        }
        val intent = Intent(this, ProductDetailActivity::class.java)
        adapter?.onItemClickListener = { position ->
            val product = productList[position]
            Log.d("Product", "Product: ${product.name} clicked at position $position")
            intent.putExtra(ProductListActivity.PRODUCT_ID_KEY, product.id.toString())
            startActivity(intent)
        }
        updateVisibility(productList)

    }
    //search for product by name
    fun searchProduct(){
        binding.searchView.apply {

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                //when search is submitted, filter the product list by name
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        Log.d("Product", "Search query: $query")
                        adapter?.filterByName(query)
                    }
                    return true
                }
                //change item when text in search is changed
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        Log.d("Product", "Search text changed: $newText")
                       val filterProduct =  adapter?.filterByName(newText) ?: emptyList()
                        updateVisibility(filterProduct)
                    }
                    return true
                }
            })
        }
    }
    //update visibility of product list and no products text
    private fun updateVisibility(list: List<Product>) {
        if (list.isEmpty()) {
            binding.txtNoProducts.visibility = android.view.View.VISIBLE
            binding.rvProductList.visibility = android.view.View.GONE
        } else {
            binding.txtNoProducts.visibility = android.view.View.GONE
            binding.rvProductList.visibility = android.view.View.VISIBLE
        }
    }
}