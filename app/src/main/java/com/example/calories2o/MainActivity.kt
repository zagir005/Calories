package com.example.calories2o

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.calories2o.core.database.FoodDao
import com.example.calories2o.core.network.FoodApiService
import com.example.calories2o.core.network.Network


class MainActivity : AppCompatActivity() {

    lateinit var foodDatabaseDao: FoodDao
    lateinit var apiService: FoodApiService
    private lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = application as App

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.activity_fragment_container
        ) as NavHostFragment

        val navController = navHostFragment.navController

        foodDatabaseDao = app.foodDatabase.getFoodDao()
        apiService = Network.foodInfoApi


    }

}
fun isOnline(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}