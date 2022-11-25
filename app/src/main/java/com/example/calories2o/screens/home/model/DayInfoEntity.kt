package com.example.calories2o.screens.home.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_info")
data class DayInfoEntity(
    @PrimaryKey val day: String,
    @ColumnInfo val maxCalories: Double = 0.0,
    @ColumnInfo val maxFat: Float = 0f,
    @ColumnInfo val maxProtein: Float = 0f,
    @ColumnInfo val maxUglevod: Float = 0f,
)
