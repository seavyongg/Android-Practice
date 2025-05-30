package com.app.retrofit_practice

import RetrofitApi
import retrofit2.Retrofit

object RetrofitClient {
    private const val BASE_URL = "https://api.rumdul.store/"

    //create a Retrofit instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()
    }

    val api: RetrofitApi by lazy {
        retrofit.create(RetrofitApi::class.java) // create an instance of RetrofitApi
    }

}
