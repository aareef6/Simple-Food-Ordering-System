package com.example.androidmainassessment3.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.androidmainassessment3.room.dao.ProductDao
import com.example.androidmainassessment3.room.dao.ProfileDao
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.room.model.Profile

class ProductRepo(var productDao: ProductDao) {


    var getAllProduct: LiveData<List<Product>> = productDao.getProduct()

    suspend fun insertProduct(product: Product){
        return productDao.insertProduct(product)
    }

    suspend fun updateProduct(product: Product){
        return productDao.updateProduct(product)
    }

    fun getSingleProductData(id: Int): LiveData<Product> = productDao.getSingleProductData(id)

    suspend fun deleteSingleTaskData(id: Int) = productDao.deleteSingleTaskData(id)


}