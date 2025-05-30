package com.app.retrofit_practice

import com.google.gson.annotations.SerializedName

data class Comments(
    val postId:Int,
    val id: Int,
    val name: String,
    val email: String,
    @SerializedName("body")
    val description: String
)
