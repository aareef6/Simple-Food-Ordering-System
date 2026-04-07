package com.example.androidmainassessment3.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.androidmainassessment3.room.model.Cart
import com.example.androidmainassessment3.room.model.Profile

@Dao
interface CartDao {

    @Insert
    suspend fun insertCart(cart: Cart)

    @Query("Select * From Cart where userId= :id")
    fun getProfile(id: String) : LiveData<List<Cart>>

    @Query("Delete from Cart where id=:id")
    suspend fun deleteCartWithId(id: Int)

    @Query("Delete from Cart where userId=:id")
    suspend fun deleteAllCartProduct(id: String)





}