package com.example.androidmainassessment3.room.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cart(
    @PrimaryKey(autoGenerate = true)
    var id: Int =0,

    var productId: Int,
    var image: String,
    var productName: String,
    var catogory: String,
    var desc: String,
    var Price: String,

    var userId: String,
    var qty: String="1"

)
