package com.example.calories2o.screens.search.domain

import com.example.calories2o.core.database.FoodDao
import com.example.calories2o.core.network.FoodApiService
import com.example.calories2o.screens.search.model.FoodEntity

class FoodRepository(
    private val apiService: FoodApiService,
    private val foodDatabase: FoodDao
    ) {

    suspend fun searchFood(text: String, count: Int = 20): List<FoodEntity> {
        val foodList = mutableListOf<FoodEntity>()

        val list = apiService.searchFood(text, count)

        list.result.foodList.forEach {
                if (
                    it.food.nutrients.calorie?.toFloatOrNull() != null &&
                    it.food.nutrients.fat?.toFloatOrNull() != null &&
                    it.food.nutrients.carbohydrates?.toFloatOrNull() != null &&
                    it.food.nutrients.protein?.toFloatOrNull() != null
                ){
                    foodList.add(
                        FoodEntity(
                            it.food.name,
                            it.food.nutrients.calorie.toFloat(),
                            it.food.nutrients.protein.toFloat(),
                            it.food.nutrients.fat.toFloat(),
                            it.food.nutrients.carbohydrates.toFloat(),
                            0,0
                        )
                    )
                }
        }
        return foodList
    }


    suspend fun addMyFood(food: FoodEntity){
        foodDatabase.insertFood(food.apply {
            myDish = 1
        })
    }

    suspend fun getMyFood(): List<FoodEntity>{
        return foodDatabase.getAllMyDishes()
    }

    suspend fun deleteFood(food: FoodEntity){
        foodDatabase.deleteFood(food)
    }

}