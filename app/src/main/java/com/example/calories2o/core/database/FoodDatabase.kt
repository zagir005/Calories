package com.example.calories2o.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.calories2o.screens.home.model.DayInfoEntity
import com.example.calories2o.screens.home.model.DinnerInfoEntity
import com.example.calories2o.screens.search.model.FoodEntity


@Database(entities = [FoodEntity::class, DinnerInfoEntity::class, DayInfoEntity::class], version = 1)
abstract class FoodDatabase: RoomDatabase() {
    abstract fun getFoodDao(): FoodDao
}