package com.example.androidmainassessment3.model

import com.example.androidmainassessment3.room.model.Cart
import com.example.androidmainassessment3.room.model.Product

data class OrderDetails (
    val orderId: Int,
    val userId: String,
    val date: String,
    val items: List<Product>
)