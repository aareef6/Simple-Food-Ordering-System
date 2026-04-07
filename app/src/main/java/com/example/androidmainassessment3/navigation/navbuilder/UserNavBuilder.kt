package com.example.androidmainassessment3.navigation.navbuilder

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidmainassessment3.navigation.NavRoute
import com.example.androidmainassessment3.navigation.screens.UserCartScreen
import com.example.androidmainassessment3.navigation.screens.UserMainScreen
import com.example.androidmainassessment3.navigation.screens.UserOrderScreen
import com.example.androidmainassessment3.viewmodel.model.CartViewModel
import com.example.androidmainassessment3.viewmodel.model.OrderViewModel
import com.example.androidmainassessment3.viewmodel.model.ProductViewModel
import com.example.androidmainassessment3.viewmodel.model.ProfileViewModel


@Composable
fun UserNavBuilder(navController: NavHostController,imageViewModel: ProfileViewModel,
               productViewModel: ProductViewModel,cartViewModel: CartViewModel,orderViewModel: OrderViewModel,
               context: Context,isAdmin: Boolean){
    NavHost(
        navController,
        startDestination = NavRoute.UserMainScreen.path)

    {

        userMainScreen(navController,this,imageViewModel,productViewModel,cartViewModel)
        userCartScreen(navController,this,imageViewModel,productViewModel,cartViewModel,orderViewModel)
        userOrderScreen(navController,this,imageViewModel,productViewModel,cartViewModel,orderViewModel)

    }
}


fun userMainScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder,
                   imageViewModel: ProfileViewModel,productViewModel: ProductViewModel,cartViewModel: CartViewModel) {
    navGraphBuilder.composable(NavRoute.UserMainScreen.path) {
        UserMainScreen(navController,imageViewModel,productViewModel,cartViewModel)
    }
}

fun userCartScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder,
                   imageViewModel: ProfileViewModel,productViewModel: ProductViewModel,cartViewModel: CartViewModel,orderViewModel: OrderViewModel) {
    navGraphBuilder.composable(NavRoute.UserCartScreen.path) {
        UserCartScreen(navController,imageViewModel,productViewModel,cartViewModel,orderViewModel)
    }
}

fun userOrderScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder,
                   imageViewModel: ProfileViewModel,productViewModel: ProductViewModel,cartViewModel: CartViewModel,orderViewModel: OrderViewModel) {
    navGraphBuilder.composable(NavRoute.UserOrderScreen.path) {
        UserOrderScreen(navController,imageViewModel,productViewModel,cartViewModel,orderViewModel)
    }
}