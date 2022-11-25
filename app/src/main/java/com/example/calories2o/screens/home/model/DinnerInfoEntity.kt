package com.example.calories2o.screens.home.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dinner_info")
data class DinnerInfoEntity(
    @ColumnInfo val name: String,
    @ColumnInfo val day: String,
    @ColumnInfo val calories: Double,
    @ColumnInfo val fat: Float,
    @ColumnInfo val protein: Float,
    @ColumnInfo val uglevod: Float,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
