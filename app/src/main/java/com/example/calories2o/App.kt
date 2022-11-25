package com.example.calories2o

import android.app.Application
import androidx.room.Room
import com.example.calories2o.core.database.FoodDatabase

class App: Application() {

    lateinit var foodDatabase: FoodDatabase

    override fun onCreate() {
        super.onCreate()

        foodDatabase = Room.databaseBuilder(
            this,
            FoodDatabase::class.java,
            "FoodDatabase"
        ).build()

        instance = this
    }

    companion object{
        lateinit var instance: App
            private set

    }
}