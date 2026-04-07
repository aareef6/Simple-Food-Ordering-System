package com.example.androidmainassessment3.navigation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androidmainassessment3.viewmodel.model.ProfileViewModel

@Composable
fun AdminProductScreen(navController: NavHostController, imageViewModel: ProfileViewModel){
    Text("AdminProductScreen", modifier = Modifier.padding(top = 200.dp))
}