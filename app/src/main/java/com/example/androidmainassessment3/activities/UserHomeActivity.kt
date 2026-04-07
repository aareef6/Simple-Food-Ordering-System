package com.example.androidmainassessment3.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.androidmainassessment3.R
import com.example.androidmainassessment3.helpers.SharedPrefHelper
import com.example.androidmainassessment3.navigation.navbuilder.UserNavBuilder
import com.example.androidmainassessment3.repository.CartRepo
import com.example.androidmainassessment3.repository.OrderRepo
import com.example.androidmainassessment3.repository.ProductRepo
import com.example.androidmainassessment3.repository.ProfileRepo
import com.example.androidmainassessment3.room.FoodDB
import com.example.androidmainassessment3.room.dao.CartDao
import com.example.androidmainassessment3.ui.theme.AndroidMainAssessment3Theme
import com.example.androidmainassessment3.viewmodel.factory.CatViewModelFactory
import com.example.androidmainassessment3.viewmodel.factory.OrderViewModelFactory
import com.example.androidmainassessment3.viewmodel.factory.ProductViewModelFactory
import com.example.androidmainassessment3.viewmodel.factory.ProfileViewModelFactory
import com.example.androidmainassessment3.viewmodel.model.CartViewModel
import com.example.androidmainassessment3.viewmodel.model.OrderViewModel
import com.example.androidmainassessment3.viewmodel.model.ProductViewModel
import com.example.androidmainassessment3.viewmodel.model.ProfileViewModel
import kotlinx.coroutines.launch

class UserHomeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var database= FoodDB.getInstance(applicationContext)
        var profilerepo = ProfileRepo(database.profileDao)
        var productRepo = ProductRepo(database.productDao)
        var cartRepo = CartRepo(database.cartDao)
        var orderRepo = OrderRepo(database.orderDao)

        var profileViewModel: ProfileViewModel= ViewModelProvider(this, ProfileViewModelFactory(profilerepo)).get(ProfileViewModel::class.java)
        var productViewModel: ProductViewModel= ViewModelProvider(this, ProductViewModelFactory(productRepo)).get(ProductViewModel::class.java)
        var cartViewModel: CartViewModel= ViewModelProvider(this, CatViewModelFactory(cartRepo)).get(CartViewModel::class.java)
        var orderViewModel: OrderViewModel= ViewModelProvider(this, OrderViewModelFactory(orderRepo)).get(OrderViewModel::class.java)


        SharedPrefHelper.init(this)
        setContent {
            AndroidMainAssessment3Theme {
                var controller= rememberNavController()
                var isAdmin=SharedPrefHelper.getString("isadmin")
                Scaffold(

                ) { paddingValues ->
                    Column(Modifier
                        .fillMaxSize()
                        .padding(paddingValues)) {
                        UserNavBuilder(controller,profileViewModel, productViewModel ,cartViewModel,orderViewModel, applicationContext ,isAdmin.toBoolean())
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidMainAssessment3Theme {
        Greeting("Android")
    }
}