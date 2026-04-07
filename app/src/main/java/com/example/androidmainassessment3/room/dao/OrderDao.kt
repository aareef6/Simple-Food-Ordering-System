package com.example.androidmainassessment3.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.androidmainassessment3.room.model.Cart
import com.example.androidmainassessment3.room.model.Order
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.room.model.Profile

@Dao
interface OrderDao {

    @Insert
    suspend fun insertOrder(order: Order)

    @Query("Update orders set status='Accepted' where orderId=:id ")
    suspend fun updateOrder(id: Int)

    @Query("SELECT * FROM orders where orderId=:id")
    fun getStatus(id: Int): LiveData<Order>

    @Query("Update orders set status='Declined' where orderId=:id ")
    suspend fun declinedUpdateOrder(id: Int)

    @Query("SELECT * FROM orders where status='Placed'")
    suspend fun getPendingOrders(): List<Order>

    @Query("SELECT * FROM orders where status='Accepted'")
    suspend fun getAcceptedOrders(): List<Order>

    @Query("SELECT * FROM orders")
    suspend fun getAllOrders(): List<Order>

    @Query("SELECT * FROM orders where userId=:userId")
    suspend fun getUserOrders(userId: String): List<Order>

    @Query("SELECT * FROM Product WHERE id IN (:ids)")
    suspend fun getCartItemsByIds(ids: List<Int>): List<Product>



}