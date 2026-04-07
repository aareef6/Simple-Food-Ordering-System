package com.example.androidmainassessment3.commoncomposable.bottombars

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderOuter
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.IncompleteCircle
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.BorderOuter
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.IncompleteCircle
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.PopUpToBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidmainassessment3.R
import com.example.androidmainassessment3.navigation.NavRoute
import com.example.androidmainassessment3.ui.theme.BackColor
import com.example.androidmainassessment3.ui.theme.ButtonColor
import com.example.androidmainassessment3.ui.theme.PrimaryGreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomBar(navController: NavController) {
    val buttonColor = PrimaryGreen

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomAppBar(
        modifier = Modifier.height(100.dp),
        containerColor = Color.White,
        tonalElevation = BottomAppBarDefaults.ContainerElevation
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomBarItem(
                title = "All",
                isSelected = currentRoute == NavRoute.AdminMainScreen.path,
                selectedIcon =R.drawable.full,
                unselectedIcon = R.drawable.unfull,
                buttonColor = buttonColor
            ) {
                navController.navigate(NavRoute.AdminMainScreen.path) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }

            BottomBarItem(
                title = "Completed",
                isSelected = currentRoute == NavRoute.AdminPendingScreen.path,
                selectedIcon = R.drawable.completed,
                unselectedIcon = R.drawable.uncompleted,
                buttonColor = buttonColor
            ) {
                navController.navigate(NavRoute.AdminPendingScreen.path) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }

            BottomBarItem(
                title = "Pending" ,
                isSelected = currentRoute == NavRoute.AdminCompletedScreen.path,
                selectedIcon = R.drawable.pending,
                unselectedIcon = R.drawable.unpending,
                buttonColor = buttonColor
            ) {
                navController.navigate(NavRoute.AdminCompletedScreen.path) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}

@Composable
fun BottomBarItem(
    title: String,
    isSelected: Boolean,
    selectedIcon: Int,
    unselectedIcon: Int,
    buttonColor: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(16.dp))
            .background(if (isSelected) buttonColor.copy(alpha = 0.15f) else Color.Transparent)
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(if (isSelected) selectedIcon else unselectedIcon),
            contentDescription = title,
            modifier = Modifier.size(28.dp)
        )
        Text(
            text = title,
            fontSize = 12.sp,
            color = if (isSelected) buttonColor else Color.Gray
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomAppPreview() {
    MaterialTheme {
        var controller = rememberNavController()
        MyBottomBar(controller)
    }
}