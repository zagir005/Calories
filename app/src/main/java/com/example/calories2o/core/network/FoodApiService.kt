package com.example.calories2o.core.network

import com.example.calories2o.screens.search.model.FoodSearch
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FoodApiService {
    @Multipart
    @POST("food_search/search")
    suspend fun searchFood(
        @Part("query[name]") name: String,
        @Part("query[count_on_page]") count: Int = 10,
        @Part("query[page]") page: Int = 1
        ): FoodSearch
}
