package com.example.androidmainassessment3.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.androidmainassessment3.model.OrderDetails
import com.example.androidmainassessment3.room.dao.CartDao
import com.example.androidmainassessment3.room.dao.OrderDao
import com.example.androidmainassessment3.room.dao.ProductDao
import com.example.androidmainassessment3.room.dao.ProfileDao
import com.example.androidmainassessment3.room.model.Cart
import com.example.androidmainassessment3.room.model.Order
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.room.model.Profile

class OrderRepo(var ordreDao: OrderDao) {

    suspend fun addOrder(order: Order){
        return ordreDao.insertOrder(order)
    }

    suspend fun updateOrder(id:Int){
        return ordreDao.updateOrder(id)
    }


    fun getStatusOrder(id:Int): LiveData<Order> =ordreDao.getStatus(id)

    suspend fun declinedUpdateOrder(id:Int){
        return ordreDao.declinedUpdateOrder(id)
    }


    suspend fun getPendingOrderDetails(): List<OrderDetails> {
        val orders = ordreDao.getPendingOrders()
        val orderDetailsList = mutableListOf<OrderDetails>()

        for (order in orders) {
            val ids = order.cartItemIds.split(",").map { it.toInt() }
            val cartItems = ordreDao.getCartItemsByIds(ids)

            orderDetailsList.add(
                OrderDetails(
                    orderId = order.orderId,
                    userId = order.userId,
                    date = order.date,
                    items = cartItems
                )
            )
        }
        return orderDetailsList
    }

    suspend fun getAcceptedOrderDetails(): List<OrderDetails> {
        val orders = ordreDao.getAcceptedOrders()
        val orderDetailsList = mutableListOf<OrderDetails>()

        for (order in orders) {
            val ids = order.cartItemIds.split(",").map { it.toInt() }
            val cartItems = ordreDao.getCartItemsByIds(ids)

            orderDetailsList.add(
                OrderDetails(
                    orderId = order.orderId,
                    userId = order.userId,
                    date = order.date,
                    items = cartItems
                )
            )
        }
        return orderDetailsList
    }

    suspend fun getAllOrderDetails(): List<OrderDetails> {
        val orders = ordreDao.getAllOrders()
        val orderDetailsList = mutableListOf<OrderDetails>()

        for (order in orders) {
            val ids = order.cartItemIds.split(",").map { it.toInt() }
            val cartItems = ordreDao.getCartItemsByIds(ids)

            orderDetailsList.add(
                OrderDetails(
                    orderId = order.orderId,
                    userId = order.userId,
                    date = order.date,
                    items = cartItems
                )
            )
        }
        return orderDetailsList
    }

    suspend fun getUserOrders(userId: String): List<OrderDetails> {
        val orders = ordreDao.getUserOrders(userId)
        val orderDetailsList = mutableListOf<OrderDetails>()

        for (order in orders) {
            val ids = order.cartItemIds.split(",").map { it.toInt() }
            val cartItems = ordreDao.getCartItemsByIds(ids)

            orderDetailsList.add(
                OrderDetails(
                    orderId = order.orderId,
                    userId = order.userId,
                    date = order.date,
                    items = cartItems
                )
            )
        }
        return orderDetailsList
    }



}