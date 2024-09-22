package com.example.products_roshan.api

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitInstance {

    private val BASE_URL="https://dummyjson.com/"


    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message ->
                println("LOG-APP: $message")
            }).apply {
            level= HttpLoggingInterceptor.Level.BODY
        })
        .build()


    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json {ignoreUnknownKeys = true} .asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val retrofitService: ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }

}