package com.app.retrofit_practice


import Categories
import CategoryItem
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.retrofit_practice.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CategoryAdapter
    private var categories: List<CategoryItem> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        fetchCategories()

    }
    fun fetchCategories(){
        RetrofitClient.api.getCategories().enqueue(object : Callback<Categories>{
            override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
                if(response.isSuccessful){
                    binding.progressBar.isVisible = false
                    binding.recyclerView.isVisible = true
                    categories = response.body()?.data?.data as List<CategoryItem>
                    adapter = CategoryAdapter(categories)
                    binding.recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<Categories>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Failed: ${t.message}",
                    Toast.LENGTH_SHORT
                )
            }

        } )
    }


}
