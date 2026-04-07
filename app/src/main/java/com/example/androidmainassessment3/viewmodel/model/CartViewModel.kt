package com.example.androidmainassessment3.viewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmainassessment3.repository.CartRepo
import com.example.androidmainassessment3.repository.ProductRepo
import com.example.androidmainassessment3.repository.ProfileRepo
import com.example.androidmainassessment3.room.model.Cart
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.room.model.Profile
import kotlinx.coroutines.launch

class CartViewModel(var cartRepo: CartRepo): ViewModel() {

    fun getCartVm(id:String): LiveData<List<Cart>> {
       return  cartRepo.getCart(id)
    }

    fun insertCart(cart: Cart) =
        viewModelScope.launch {
            cartRepo.addCart(cart)
        }

    fun deleteCartWithId(id: Int) =
        viewModelScope.launch {
            cartRepo.deleteCartWithId(id)
        }

    fun deleteAllCartProduct(id: String) =
        viewModelScope.launch {
            cartRepo.deleteAllCartProduct(id)
        }



}