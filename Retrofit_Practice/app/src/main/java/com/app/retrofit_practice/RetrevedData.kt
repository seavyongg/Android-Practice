package com.app.retrofit_practicepackage
import com.app.retrofit_practice.Posts
import com.app.retrofit_practice.RetrofitApi
import com.app.retrofit_practice.databinding.ActivityRetrivedDataBinding
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrevedData : AppCompatActivity() {
    lateinit var binding: ActivityRetrivedDataBinding
    private var urlApi = "https://jsonplaceholder.typicode.com/"
    var postsList = ArrayList<Posts>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrivedDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // showPosts()
        getPos()
    }
    fun showPosts(){
        var retrofit = Retrofit.Builder(
        )
            .baseUrl(urlApi) //put the base url of the api
            .addConverterFactory( //convert to json format
                GsonConverterFactory.create()
            )
            .build()
        var retrofitApi : RetrofitApi = retrofit.create(RetrofitApi::class.java) // create the api interface
        var call = retrofitApi.getAllPosts()

        // Asynchronous call to the API
        call.enqueue(object: Callback<List<Posts>> {
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                if(!response.isSuccessful){
                    binding.txtUserId.text = "Error"
                    binding.txtId.text = "Error"
                    binding.txtTitle.text = "Error"
                    binding.txtBody.text = "Error"
                }
                postsList = response.body() as ArrayList<Posts> // get the response body
                binding.txtUserId.text = postsList[1].userId.toString()
                binding.txtId.text = postsList[1].id.toString()
                binding.txtTitle.text = postsList[1].title
                binding.txtBody.text = postsList[1].subTitle
            }

            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    t.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }

        })

    }
    fun getPos(){
        //put base url
        val retrofit = Retrofit.Builder()
            .baseUrl(urlApi)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitApi : RetrofitApi = retrofit.create(RetrofitApi::class.java)
        var call = retrofitApi.getAllPosts()
        call.enqueue(object : Callback<List<Posts>>{
            override fun onResponse(call: Call<List<Posts>>, response: Response<List<Posts>>) {
                if(!response.isSuccessful){
                    binding.txtUserId.text = "Error"
                    binding.txtId.text = "Error"
                    binding.txtTitle.text = "Error"
                    binding.txtBody.text = "Error"
                }
                var postList = response.body() as ArrayList<Posts>
                binding.txtUserId.text = postList[1].userId.toString()
                binding.txtId.text = postList[1].id.toString()
                binding.txtTitle.text = postList[1].title
                binding.txtBody.text = postList[1].subTitle
            }

            override fun onFailure(call: Call<List<Posts>>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    t.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            }

        })
    }
}