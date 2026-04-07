package com.example.androidmainassessment3.navigation

  open class NavRoute(var path: String) {


      // User Screens

     object UserMainScreen: NavRoute("user_main_screen")

     object UserCartScreen: NavRoute("user_cart_screen")

     object UserOrderScreen: NavRoute("user_order_screen")

      // Admin Screens

      object AdminMainScreen: NavRoute("admin_main_screen")

      object AdminPendingScreen: NavRoute("admin_pending_screen")

      object AdminCompletedScreen: NavRoute("admin_completed_screen")

      object AdminProductScreen: NavRoute("admin_product_screen")
}