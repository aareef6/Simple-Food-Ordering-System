package com.example.androidmainassessment3.navigation.navbuilder

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidmainassessment3.navigation.NavRoute
import com.example.androidmainassessment3.navigation.screens.AdminCompletedScreen
import com.example.androidmainassessment3.navigation.screens.AdminMainScreen
import com.example.androidmainassessment3.navigation.screens.AdminPendingScreen
import com.example.androidmainassessment3.navigation.screens.AdminProductScreen
import com.example.androidmainassessment3.viewmodel.model.OrderViewModel

import com.example.androidmainassessment3.viewmodel.model.ProfileViewModel


@Composable
fun AdminNavBuilder(navController: NavHostController,imageViewModel: ProfileViewModel,
               videoViewModel: ProfileViewModel,profileViewModel: ProfileViewModel,orderViewModel: OrderViewModel,
               context: Context,isAdmin: Boolean){
    NavHost(
        navController,
        startDestination =  NavRoute.AdminMainScreen.path)

    {
        adminMainScreen(navController,this,videoViewModel,context,orderViewModel)
        adminPendingScreen(navController,this,videoViewModel,context,orderViewModel)
        adminCompletedScreen(navController,this,videoViewModel,context,orderViewModel)

    }
}


fun adminMainScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder,videoViewModel: ProfileViewModel,context: Context,orderViewModel: OrderViewModel){
    navGraphBuilder.composable(NavRoute.AdminMainScreen.path) {
        AdminMainScreen(navController,videoViewModel,orderViewModel, )
    }
}

fun adminPendingScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder,videoViewModel: ProfileViewModel,context: Context,orderViewModel: OrderViewModel){
    navGraphBuilder.composable(NavRoute.AdminPendingScreen.path) {
        AdminPendingScreen(navController,videoViewModel,orderViewModel )
    }
}

fun adminCompletedScreen(navController: NavHostController, navGraphBuilder: NavGraphBuilder,videoViewModel: ProfileViewModel,context: Context,orderViewModel: OrderViewModel){
    navGraphBuilder.composable(NavRoute.AdminCompletedScreen.path) {
        AdminCompletedScreen(navController,videoViewModel,orderViewModel )
    }
}


