package com.example.androidmainassessment3.navigation.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.androidmainassessment3.R
import com.example.androidmainassessment3.activities.AdminProductsActivity
import com.example.androidmainassessment3.activities.GreenPrimary
import com.example.androidmainassessment3.activities.LoginActivity
import com.example.androidmainassessment3.activities.UserHomeActivity
import com.example.androidmainassessment3.helpers.SharedPrefHelper
import com.example.androidmainassessment3.navigation.NavRoute
import com.example.androidmainassessment3.repository.ProductRepo
import com.example.androidmainassessment3.repository.ProfileRepo
import com.example.androidmainassessment3.room.FoodDB
import com.example.androidmainassessment3.room.model.Cart
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.ui.theme.BackColor
import com.example.androidmainassessment3.ui.theme.DarkBackColor
import com.example.androidmainassessment3.viewmodel.factory.ProductViewModelFactory
import com.example.androidmainassessment3.viewmodel.factory.ProfileViewModelFactory
import com.example.androidmainassessment3.viewmodel.model.CartViewModel
import com.example.androidmainassessment3.viewmodel.model.ProductViewModel
import com.example.androidmainassessment3.viewmodel.model.ProfileViewModel

@Composable
fun UserMainScreen(controller: NavController,profileViewModel: ProfileViewModel,productViewModel: ProductViewModel,cartViewModel: CartViewModel) {
    var context= LocalContext.current
    SharedPrefHelper.init(context)

    Scaffold(
        topBar = {
            TopBarSection(controller)
        }
    ) { innerPadding ->


        LazyColumn( modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth())
        {
            item { GreetingSection() }
            item { CoffeeCardsRow() }
            item { CategorySection() }
            item { ProcutsSection(productViewModel,cartViewModel) }
        }
    }
}

@Composable
fun TopBarSection(controller: NavController) {
    var context=LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()

            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Welcome", fontSize = 14.sp, color = Color.Gray)
        Row {
            IconButton(onClick = { expanded=!expanded }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color(0xFF006400)
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("My Cart") },
                        onClick = {
                            expanded = false
                            controller.navigate(NavRoute.UserCartScreen.path)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("My Orders") },
                        onClick = {
                            expanded = false
                            controller.navigate(NavRoute.UserOrderScreen.path)
                        }
                    )
                }

            }
            IconButton(onClick = {
                SharedPrefHelper.clearPreference()
                var intent= Intent(context, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Cart",
                    tint = Color(0xFF006400)
                )
            }
        }
    }
}

@Composable
fun GreetingSection() {
    var UserEmail=SharedPrefHelper.getString("email")
    Text(
        text = UserEmail,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
    )
}


@Composable
fun CoffeeCardsRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FoodCard("Meals")
        FoodCard("Creamy ")
    }
}

@Composable
fun FoodCard(title: String) {
    Card(
        modifier = Modifier
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF006400)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .background(Color.White, shape = CircleShape)
            ){
                Image(painter = painterResource(R.drawable.food_back),
                    "food_back",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(title, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 5.dp))

        }
    }
}

@Composable
fun CategorySection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Categories", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
            CategoryCard("Beverages", "41 Menus", Icons.Default.LocalCafe)
            CategoryCard("Foods", "37 Menus", Icons.Default.Fastfood)
            CategoryCard("Beverages", "41 Menus", Icons.Default.LocalCafe)
            CategoryCard("Foods", "37 Menus", Icons.Default.Fastfood)
            CategoryCard("Beverages", "41 Menus", Icons.Default.LocalCafe)
            CategoryCard("Foods", "37 Menus", Icons.Default.Fastfood)
        }
    }
}

@Composable
fun CategoryCard(title: String, subtitle: String, icon: ImageVector) {
    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = title, tint = Color(0xFF006400))
            Text(title, fontWeight = FontWeight.Bold)
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ProcutsSection(productViewModel: ProductViewModel,cartViewModel: CartViewModel) {
    val Products by productViewModel.getProductVM.observeAsState(emptyList())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Menus", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text("More", color = Color(0xFF006400))
    }

    LazyVerticalStaggeredGrid (
        columns = StaggeredGridCells.Fixed(2),
        modifier =
            Modifier.fillMaxWidth()
                .padding(16.dp)
                .heightIn(max=1000.dp)
    )
    {
        items (Products) { product ->
            ProductItem(product,cartViewModel)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun ProductItem(product: Product,cartViewModel: CartViewModel) {
    var context=LocalContext.current

    var userEmail= SharedPrefHelper.getString("email")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 10.dp),

        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
        ) {
            GlideImage(
                model = Uri.parse(product.image),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = "lskdj")

            Column(
                modifier = Modifier
                    .padding(0.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = product.productName,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 6.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Text(
                    text = "$ ${product.Price}",
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 6.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )


                Button(
                    onClick ={
                        var cart= Cart(image=product.image,  productName=product.productName,catogory=product.catogory,desc=product.desc,Price=product.Price,userId=userEmail, productId = product.id)
                        cartViewModel.insertCart(cart)
                        Toast.makeText(context,"Food Added to Cart", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.padding(bottom = 5.dp)
                        .height(35.dp).padding(start = 5.dp,),
                    border= BorderStroke(2.dp,GreenPrimary),
                    shape = RoundedCornerShape(30.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Add To Cart", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }




//    Card(elevation = CardDefaults.cardElevation(12.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = Color.White
//        ), modifier = Modifier.padding(4.dp).clickable(true, onClick = {Log.d("clickable","click")}),
//        border = BorderStroke(1.dp, Color.Black)) {
//        Column(Modifier.fillMaxSize().padding(12.dp)) {
//            Text(gallery.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
//
//            Spacer(Modifier.height(6.dp))
//
//            Text(gallery.description, fontSize = 16.sp)
//
//            Spacer(Modifier.height(6.dp))
//
//                Log.d("hello","this is image")
//                GlideImage(
//                    model = gallery.image.toUri(),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(180.dp)
//                        .width(200.dp)
//                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
//                    contentScale = ContentScale.Crop,
//                    contentDescription = "lskdj")
//
//
//
//
//        }
//    }

}

