package com.app.afinal.api

import com.app.afinal.model.Product
import retrofit2.http.GET

interface ProductService {
    @GET("product-list.json")
    suspend fun loadProductList(): List<Product>

}