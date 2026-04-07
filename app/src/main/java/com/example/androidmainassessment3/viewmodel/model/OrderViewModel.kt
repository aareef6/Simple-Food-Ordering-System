package com.example.androidmainassessment3.viewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmainassessment3.model.OrderDetails
import com.example.androidmainassessment3.repository.CartRepo
import com.example.androidmainassessment3.repository.OrderRepo
import com.example.androidmainassessment3.repository.ProductRepo
import com.example.androidmainassessment3.repository.ProfileRepo
import com.example.androidmainassessment3.room.model.Cart
import com.example.androidmainassessment3.room.model.Order
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.room.model.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel(var orderRepo: OrderRepo): ViewModel() {

    private val _orders = MutableStateFlow<List<OrderDetails>>(emptyList())
    val orders: StateFlow<List<OrderDetails>> = _orders

    fun insertOrder(order: Order) =
        viewModelScope.launch {
            orderRepo.addOrder(order)
        }

    fun updateOrder(id: Int) =
        viewModelScope.launch {
            orderRepo.updateOrder(id)
        }

    fun getStatusOrder(id: Int): LiveData<Order>  = orderRepo.getStatusOrder(id)


    fun declinedUpdateOrder(id: Int) {
        viewModelScope.launch {
            orderRepo.declinedUpdateOrder(id)
        }
        loadOrders()
    }




    fun loadOrders() {
        viewModelScope.launch {
            _orders.value = orderRepo.getPendingOrderDetails()
        }
    }

    fun loadAllOrders() {
        viewModelScope.launch {
            _orders.value = orderRepo.getAllOrderDetails()
        }
    }

    fun loadUserOrders(userId: String) {
        viewModelScope.launch {
            _orders.value = orderRepo.getUserOrders(userId)
        }
    }

    fun loadCompletedOrders() {
        viewModelScope.launch {
            _orders.value = orderRepo.getAcceptedOrderDetails()
        }
    }



}