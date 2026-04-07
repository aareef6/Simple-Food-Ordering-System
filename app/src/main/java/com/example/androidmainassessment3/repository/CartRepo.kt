package com.example.androidmainassessment3.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.androidmainassessment3.room.dao.CartDao
import com.example.androidmainassessment3.room.dao.ProductDao
import com.example.androidmainassessment3.room.dao.ProfileDao
import com.example.androidmainassessment3.room.model.Cart
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.room.model.Profile

class CartRepo(var cartDao: CartDao) {

    suspend fun addCart(cart: Cart){
        return cartDao.insertCart(cart)
    }

    suspend fun deleteCartWithId(id: Int){
        return cartDao.deleteCartWithId(id)
    }

    suspend fun deleteAllCartProduct(id: String){
        return cartDao.deleteAllCartProduct(id)
    }

    fun getCart(id:String): LiveData<List<Cart>> {
        return cartDao.getProfile(id)
    }


}