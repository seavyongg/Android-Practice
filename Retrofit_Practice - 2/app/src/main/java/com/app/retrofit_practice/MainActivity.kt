package com.app.retrofit_practice

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.retrofit_practice.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var urlApi = "https://jsonplaceholder.typicode.com/"
    lateinit var adapter: CommentsAdapter
    var commentsList = ArrayList<Comments>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        showPost()
    }
    fun showPost(){
        val retrofit = Retrofit.Builder()
            .baseUrl(urlApi) // put the base url of the api
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
        var retrofitApi : RetrofitApi = retrofit.create(RetrofitApi::class.java) // create the api interface
        var call = retrofitApi.getAllComments()
        call.enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: Call<List<Comments>>,
                response: Response<List<Comments>>
            ) {
                if (response.isSuccessful){
                    binding.progressBar.isVisible = false
                    binding.recyclerView.isVisible = true
                    commentsList = response.body() as ArrayList<Comments> // get the response body
                    adapter = CommentsAdapter(commentsList)
                    binding.recyclerView.adapter = adapter

                }
            }

            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    t.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }


        })
    }
}