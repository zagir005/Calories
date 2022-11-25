package com.example.calories2o.screens.search.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "food_info")
data class FoodEntity(
    @ColumnInfo val name: String,
    @ColumnInfo val calorie: Float,
    @ColumnInfo val protein: Float,
    @ColumnInfo val fat : Float,
    @ColumnInfo val carbohydrates: Float,
    @ColumnInfo(defaultValue = "0") var myDish: Int,
    @ColumnInfo(defaultValue = "0") var liked: Int,
    @PrimaryKey(autoGenerate = true) val myId: Int = 0
)