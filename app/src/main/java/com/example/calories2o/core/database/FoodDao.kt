package com.example.calories2o.core.database

import androidx.room.*
import com.example.calories2o.screens.home.model.DayInfoEntity
import com.example.calories2o.screens.home.model.DinnerInfoEntity
import com.example.calories2o.screens.search.model.FoodEntity

@Dao
interface FoodDao {
    @Query("SELECT * from food_info WHERE myDish = 1")
    suspend fun getAllMyDishes(): List<FoodEntity>

    @Insert(entity = FoodEntity::class)
    suspend fun insertFood(vararg foodEntity: FoodEntity)

    @Insert(entity = FoodEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(foodEntity: List<FoodEntity>)

    @Delete
    suspend fun deleteFood(foodEntity: FoodEntity)

    @Delete
    suspend fun deleteDinner(dinnerInfoEntity:DinnerInfoEntity)

    @Query("SELECT * from dinner_info")
    suspend fun getAllDinners(): List<DinnerInfoEntity>

    @Query("SELECT * from dinner_info WHERE day =:day")
    suspend fun getDinnersByDay(day:String): List<DinnerInfoEntity>

    @Insert(entity = DinnerInfoEntity::class)
    suspend fun insertDinner(dinnerInfoEntity: DinnerInfoEntity)

    @Insert(entity = DinnerInfoEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDinnersList(dinnerInfoEntity: List<DinnerInfoEntity>)

    @Insert(entity = DayInfoEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDayInfoEntity(dayInfoEntity: DayInfoEntity)

    @Query("SELECT * FROM day_info WHERE day = :day")
    suspend fun getDayInfo(day: String): DayInfoEntity?

    @Delete
    suspend fun deleteDayInfo(dayInfoEntity: DayInfoEntity)

    @Query("DELETE from day_info WHERE day = :day")
    suspend fun deleteDayInfo(day: String)

    suspend fun updateDayInfo(dayInfoEntity: DayInfoEntity): DayInfoEntity{
        deleteDayInfo(dayInfoEntity.day)
        insertDayInfoEntity(dayInfoEntity)
        return getDayInfo(dayInfoEntity.day)!!
    }


}