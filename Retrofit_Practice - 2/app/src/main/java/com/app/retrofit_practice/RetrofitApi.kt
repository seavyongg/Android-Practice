package com.app.retrofit_practice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

//use interface for Retrofit API calls
interface RetrofitApi {
    @GET("/comments")
    fun getAllComments(): Call<List<Comments>>
}