package com.example.androidmainassessment3.room.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val orderId: Int = 0,
    val cartItemIds: String,
    val userId: String,
    val date: String,
    var status: String="Placed"
)
