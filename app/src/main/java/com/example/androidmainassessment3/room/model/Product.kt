package com.example.androidmainassessment3.room.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    var id: Int =0,
    var image: String,
    var productName: String,
    var catogory: String,
    var desc: String,
    var Price: String
)
