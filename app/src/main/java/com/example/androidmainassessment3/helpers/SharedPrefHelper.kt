package com.example.androidmainassessment3.helpers

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key

object SharedPrefHelper {

    lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context){
        sharedPreferences=context.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE)
    }

    fun putString(key: String,value: String) {
        sharedPreferences.edit().putString(key,value).apply()
    }

    fun getString(key: String): String{
        return sharedPreferences.getString(key,"").toString()
    }

    fun clearPreference(){
        sharedPreferences.edit().clear().apply()
    }

}