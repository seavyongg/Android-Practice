package com.app.afinal.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.doOnAttach
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.app.afinal.R
import com.app.afinal.adapter.ProductAdapter
import com.app.afinal.api.ApiClient
import com.app.afinal.api.ProductService
import com.app.afinal.databinding.ActivityProductListBinding
import com.app.afinal.model.Product
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductListActivity : BaseActivity() {
    private lateinit var binding: ActivityProductListBinding
    private var adapter: ProductAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        binding.icCart.setOnClickListener{
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        setContentView(binding.root)
        loadProductList()
        searchProduct()
    }
    fun loadProductList(){
        lifecycleScope.launch {
            try{
                val productList = ApiClient.productService.loadProductList()
                displayProductList(productList)
            }catch (ex: Exception) {
                Log.d("ProductListActivity", "Error loading product list: ${ex.message}")
                showDialog("Error", "Failed to load product list.")
            }
        }

    }
    private fun displayProductList(productList: List<Product>) {
        adapter = ProductAdapter(productList)
        adapter?.onItemClickListener = {
            position: Int -> productList[position]
            Log.d("Product", "Product: ${productList[position].name} clicked at position $position")
            val intent = Intent(this, ProductDetailActivity::class.java).apply {
                val product = productList[position]
                putExtra(PRODUCT_ID_KEY, product.id.toString())
                Log.d("product", "Navigating to ProductDetailActivity with ID: ${product.id}")
            }
            startActivity(intent)

        }
        binding.rvProductList.adapter = adapter
        binding.rvProductList.layoutManager = GridLayoutManager(this, 2)
        updateViibility(productList)
    }
    private fun updateViibility(list: List<Product>){
        if(list.isEmpty()){
            binding.txtNoProducts.visibility = View.VISIBLE
            binding.rvProductList.visibility = View.GONE
        }else{
            binding.txtNoProducts.visibility = View.GONE
            binding.rvProductList.visibility = View.VISIBLE
        }
    }
    private fun searchProduct(){
        binding.edtSearch.doAfterTextChanged { keyword ->
            Log.d("ProductListActivity", "Search keyword: $keyword")
           val filterProduct =  adapter?.filterByName(keyword.toString()) ?: emptyList()
            updateViibility(filterProduct)
        }



    }

companion object {
    const val PRODUCT_ID_KEY = "Product_ID"
}
}