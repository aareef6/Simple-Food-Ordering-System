package com.example.androidmainassessment3.commoncomposable.popups


import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.mutableStateOf

import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties




@Composable
fun MyPopWindowDialog(openDialog: MutableState<Boolean>) {
    val popupWidth = 300.dp
    val popupHeight = 100.dp
    if (openDialog.value) {
        // on below line we are updating button
        // title value.
//        buttonTitle.value = "Hide Pop Up"
        // on below line we are adding pop up
        Popup(
            // on below line we are adding
            // alignment and properties.
            alignment = Alignment.TopStart,
            properties = PopupProperties()
        ) {

            // on the below line we are creating a box.
            Box(
                // adding modifier to it.
                Modifier
                    .size(popupWidth, popupHeight)
                    .padding(top = 5.dp, end = 10.dp)
                    // on below line we are adding background color
                    .background(Color.White, RoundedCornerShape(10.dp))
                    // on below line we are adding border.
                    .border(1.dp, color = Color.Black, RoundedCornerShape(10.dp))

            ) {
                // on below line we are adding column
                Column (
                    // on below line we are adding modifier to it.
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    // on below line we are adding horizontal and vertical
                    // arrangement to it.
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // on below line we are adding text for our pop up
                    Text(
                        // on below line we are specifying text
                        text = "Welcome",
                        // on below line we are specifying color.
                        color = Color.Black,
                        // on below line we are adding padding to it
                        modifier = Modifier.padding(vertical = 5.dp),
                        // on below line we are adding font size.
                        fontSize = 16.sp
                    )
                }
            }
        }
    }


}