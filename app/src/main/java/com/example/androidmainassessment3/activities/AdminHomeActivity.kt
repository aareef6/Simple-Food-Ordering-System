package com.example.androidmainassessment3.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.androidmainassessment3.R
import com.example.androidmainassessment3.commoncomposable.bottombars.MyBottomBar
import com.example.androidmainassessment3.helpers.SharedPrefHelper
import com.example.androidmainassessment3.navigation.navbuilder.AdminNavBuilder
import com.example.androidmainassessment3.repository.OrderRepo
import com.example.androidmainassessment3.repository.ProfileRepo
import com.example.androidmainassessment3.room.FoodDB
import com.example.androidmainassessment3.ui.theme.AndroidMainAssessment3Theme
import com.example.androidmainassessment3.viewmodel.factory.OrderViewModelFactory
import com.example.androidmainassessment3.viewmodel.factory.ProfileViewModelFactory
import com.example.androidmainassessment3.viewmodel.model.OrderViewModel
import com.example.androidmainassessment3.viewmodel.model.ProfileViewModel

class AdminHomeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var database= FoodDB.getInstance(applicationContext)
        var profilerepo = ProfileRepo(database.profileDao)
        var orderRepo = OrderRepo(database.orderDao)

        var profileViewModel: ProfileViewModel= ViewModelProvider(this, ProfileViewModelFactory(profilerepo)).get(ProfileViewModel::class.java)
        var orderViewModel: OrderViewModel= ViewModelProvider(this, OrderViewModelFactory(orderRepo)).get(OrderViewModel::class.java)

        SharedPrefHelper.init(this)
        setContent {
            AndroidMainAssessment3Theme {
                 var controller = rememberNavController()
                 var context= LocalContext.current
                 var isAdmin = SharedPrefHelper.getString("isadmin")
                var expanded by remember { mutableStateOf(false) }

                 Scaffold(
                     topBar = {
                         TopAppBar(
                             title = { Text(stringResource(R.string.app_name), fontWeight = FontWeight.Bold) },
                             colors = TopAppBarDefaults.topAppBarColors(Color.White),
                             actions = {
                                 IconButton(
                                     onClick = { expanded = !expanded },
                                     ) {
                                     Icon(painterResource(R.drawable.menu) , contentDescription = "Menu")
                                 }
                                 DropdownMenu(
                                     expanded = expanded,
                                     onDismissRequest = { expanded = false },
                                 ) {
                                     DropdownMenuItem(
                                         text = { Text("Show Menu") },
                                         onClick = {
                                             expanded = false
                                             context.startActivity(
                                                 Intent(context, AdminProductsActivity::class.java)
                                             )
                                         }
                                     )
                                     DropdownMenuItem(
                                         text = { Text("LogOut") },
                                         onClick = {
                                             expanded = false
                                             SharedPrefHelper.clearPreference()
                                             val intent = Intent(context, LoginActivity::class.java)
                                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                             startActivity(intent)
//                                             context.startActivity(
//                                                 Intent(context, AdminProductsActivity::class.java)
                                             //)
                                         }
                                     )
                                 }
                             },


                         )
                     },

                     bottomBar = {MyBottomBar(controller)}
                 ) { paddingValues ->
                     Column(
                         Modifier
                             .fillMaxSize()
                             .padding(paddingValues)
                     ) {
                         AdminNavBuilder(
                             controller,
                             profileViewModel,
                             profileViewModel,
                             profileViewModel,
                             orderViewModel,
                             applicationContext,
                             isAdmin.toBoolean()
                         )
                     }
                 }
             }
        }
    }
}
