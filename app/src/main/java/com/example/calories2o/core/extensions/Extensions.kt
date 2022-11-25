package com.example.calories2o.core.extensions

import android.util.Log
import okhttp3.internal.trimSubstring

fun Any?.logPrint(){
    Log.d("MYTAG",this.toString())
}

fun logPrint(msg: Any?, tag: String = "MYTAG"){
    Log.d(msg.toString(),tag)
}

fun String.divideFoodName(): DividedFoodName{
    val name = substringBefore('[')
    val brand = substringAfter('[').substringBefore(']')
    return DividedFoodName(name,brand)
}

data class DividedFoodName(
    val name: String,
    val brand: String
)