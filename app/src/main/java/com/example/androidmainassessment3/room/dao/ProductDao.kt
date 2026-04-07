package com.example.androidmainassessment3.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.room.model.Profile

@Dao
interface ProductDao {

    @Query("Select * from Product")
    fun getProduct() : LiveData<List<Product>>

    @Insert
    suspend fun insertProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Query("Select * from Product where id= :id")
    fun getSingleProductData(id: Int) : LiveData<Product>

    @Query("Delete from Product where id= :id")
    suspend fun deleteSingleTaskData(id: Int)


}