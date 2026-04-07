package com.example.androidmainassessment3.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidmainassessment3.repository.CartRepo
import com.example.androidmainassessment3.repository.OrderRepo
import com.example.androidmainassessment3.repository.ProductRepo
import com.example.androidmainassessment3.repository.ProfileRepo
import com.example.androidmainassessment3.viewmodel.model.CartViewModel
import com.example.androidmainassessment3.viewmodel.model.OrderViewModel
import com.example.androidmainassessment3.viewmodel.model.ProductViewModel
import com.example.androidmainassessment3.viewmodel.model.ProfileViewModel


class ProfileViewModelFactory(var profileRepo: ProfileRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(profileRepo) as T
    }
}

class ProductViewModelFactory(var productRepo: ProductRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(productRepo) as T
    }
}


class CatViewModelFactory(var cartRepo: CartRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CartViewModel(cartRepo) as T
    }
}

class OrderViewModelFactory(var orderRepo: OrderRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrderViewModel(orderRepo) as T
    }
}
