package com.example.androidmainassessment3.viewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmainassessment3.repository.ProductRepo
import com.example.androidmainassessment3.repository.ProfileRepo
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.room.model.Profile
import kotlinx.coroutines.launch

class ProductViewModel(var productRepo: ProductRepo): ViewModel() {

    var getProductVM: LiveData<List<Product>> = productRepo.getAllProduct

    fun insertProduct(product: Product) =
        viewModelScope.launch {
            productRepo.insertProduct(product)
        }
    fun updateProduct(product: Product) =
        viewModelScope.launch {
            productRepo.updateProduct(product)
        }

    fun getSingleProductData(id: Int): LiveData<Product> = productRepo.getSingleProductData(id)

    fun deleteSingleTaskData(id: Int) =
        viewModelScope.launch {
            productRepo.deleteSingleTaskData(id)
        }

}