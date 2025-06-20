package com.app.afinal.api

object ApiClient {
    //singleton pattern to create a single instance of Retrofit
    val BASE_URL = "https://smlp-pub.s3.ap-southeast-1.amazonaws.com/mad-g9/"
   val retrofit = retrofit2.Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
        .build()
    val productService: ProductService
        get() = retrofit.create(ProductService::class.java)
}