package com.example.products_roshan.api

import com.example.products_roshan.models.Category
import com.example.products_roshan.models.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("products/category/{categories}")
    suspend fun getCategory(@Path("categories") categoryName: String): Category


    @GET("products/{productId}")
    suspend fun getProductDetail(@Path("productId") productId: String): Product

}