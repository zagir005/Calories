package com.example.calories2o.core.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val baseUrl = "https://fs2.tvoydnevnik.com/api2/"

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    )
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .build()



object Network{
    val foodInfoApi = retrofit.create(FoodApiService::class.java)
}