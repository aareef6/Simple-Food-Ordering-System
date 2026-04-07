package com.example.androidmainassessment3.navigation.screens

import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.androidmainassessment3.activities.AdminAddProductActivity
import com.example.androidmainassessment3.activities.GreenPrimary
import com.example.androidmainassessment3.activities.ProductCard
import com.example.androidmainassessment3.activities.ProductListScreen
import com.example.androidmainassessment3.commoncomposable.CustomButton
import com.example.androidmainassessment3.commoncomposable.CustomText
import com.example.androidmainassessment3.commoncomposable.dialoges.ProductDeletingAlertDialog
import com.example.androidmainassessment3.helpers.SharedPrefHelper
import com.example.androidmainassessment3.room.model.Cart
import com.example.androidmainassessment3.room.model.Order
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.ui.theme.ButtonColor
import com.example.androidmainassessment3.ui.theme.CardBack
import com.example.androidmainassessment3.ui.theme.PriceBatchColor
import com.example.androidmainassessment3.ui.theme.PriceTextColor
import com.example.androidmainassessment3.ui.theme.PrimaryGreen
import com.example.androidmainassessment3.viewmodel.model.CartViewModel
import com.example.androidmainassessment3.viewmodel.model.OrderViewModel
import com.example.androidmainassessment3.viewmodel.model.ProductViewModel
import com.example.androidmainassessment3.viewmodel.model.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun UserCartScreen(controller: NavController,profileViewModel: ProfileViewModel,productViewModel: ProductViewModel,cartViewModel: CartViewModel,
    orderViewModel: OrderViewModel){
    var context=LocalContext.current
    var deletingDialogState = remember { mutableStateOf(false) }
    var imageGalleryId = remember { mutableStateOf(0) }
    Column {
        Column(Modifier.weight(1f)) {
            CartListScreen(cartViewModel,controller,imageGalleryId,deletingDialogState)
        }
        Row(Modifier.fillMaxWidth(),Arrangement.SpaceBetween) {

            var email= SharedPrefHelper.getString("email")
            val cartItemsId by cartViewModel.getCartVm(email).observeAsState(emptyList())
            CustomText(
                "Proceed to Orders",
                Modifier
                    .padding(top = 15.dp)
                    .padding(start = 15.dp),
                fontSize = 13.sp,
                FontWeight.Bold
            )

            Button(
                onClick = {
                    if (cartItemsId.isNotEmpty()) {
                        val ids = cartItemsId.joinToString(",") { it.productId.toString() }

                        val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                            Locale.getDefault()).format(Date())

                        val order = Order(
                            cartItemIds = ids,
                            userId = email,
                            date = currentDate
                        )

                        orderViewModel.insertOrder(order)
                        cartViewModel.deleteAllCartProduct(email)

                    }
                    Toast.makeText(context,"Your Order is Placed", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(end = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text =  "Click Here", color = Color.White)
            }
        }
    }
    if(deletingDialogState.value)
    {
        ProductDeletingAlertDialog(deletingDialogState,productViewModel,imageGalleryId){
            cartViewModel.deleteCartWithId(imageGalleryId.value)
            deletingDialogState.value = false
        }
    }

}

@Composable
fun CartListScreen(cartViewModel: CartViewModel,controller: NavController,
                   imageGalleryId: MutableState<Int>,
                   deletingDialogState: MutableState<Boolean>) {
    var context=LocalContext.current
    SharedPrefHelper.init(context)
    var email= SharedPrefHelper.getString("email")
    val cartData by cartViewModel.getCartVm(email).observeAsState(emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick =  {controller.navigate("user_main_screen"){popUpTo("user_main_screen"){inclusive=true} } },
                modifier = Modifier
                    .background(Color(0xFFEFEFEF), shape = CircleShape)
                    .size(40.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = GreenPrimary)
            }


            IconButton(onClick = { }) {
                Icon(Icons.Default.FilterList, contentDescription = "Filter")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if(cartData.isEmpty()){
            Box(modifier= Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Icon(Icons.Default.HourglassEmpty,"empty", Modifier.size(500.dp) )
            }
        }else{
            LazyColumn(
                modifier= Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items (cartData) { product ->
                    CartCart(product, imageGalleryId =imageGalleryId, deletingDialogState)
                }
            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CartCart(product: Cart,
             imageGalleryId: MutableState<Int>,
             deletingDialogState: MutableState<Boolean>) {


    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GlideImage(
                    model = product.image.toUri(),
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = product.productName
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = product.productName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )
                    )

                    Text(
                        text = "Category: ${product.catogory}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray
                        )
                    )

                    Text(
                        text = product.desc,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color(0xFF555555)
                        ),
                        maxLines = 2
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = PrimaryGreen,
                                    shape = RoundedCornerShape(50)
                                )
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "₹ ${product.Price}",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = {
                                imageGalleryId.value = product.id!!
                                deletingDialogState.value = true
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Next",
                                    tint = MaterialTheme.colorScheme.error)
                            }
//                            Text(text = "Remove", color = PriceTextColor, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

        }
    }

//    Card(
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(4.dp),
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Column(
//            modifier = Modifier.padding(12.dp)
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                GlideImage(
//                    model = product.image.toUri(),
//                    modifier = Modifier
//                        .size(90.dp)
//                        .clip(RoundedCornerShape(12.dp)),
//                    contentScale = ContentScale.Crop,
//                    contentDescription = "lskdj")
//
//
//                Spacer(modifier = Modifier.width(12.dp))
//
//                Column(
//                    modifier = Modifier.weight(1f)
//                ) {
//                    Text("Category: ${product.catogory}", style = MaterialTheme.typography.bodySmall)
//                    Text("Description: ${product.desc}", style = MaterialTheme.typography.bodySmall)
//
//                    Spacer(modifier = Modifier.height(6.dp))
//
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Box(
//                            modifier = Modifier
//                                .background(PriceBatchColor, shape = RoundedCornerShape(20.dp))
//                                .padding(horizontal = 12.dp, vertical = 6.dp)
//                        ) {
//                            Text(text = product.Price, color = PriceTextColor, style = MaterialTheme.typography.bodySmall)
//                        }
//
//                        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
//                            IconButton(onClick = {}) {
//                                Icon(Icons.Default.Delete, contentDescription = "Next")
//                            }
//                            Text(text = "Remove", color = PriceTextColor, style = MaterialTheme.typography.bodySmall)
//                        }
//
//
//                    }
//
//                    Spacer(modifier = Modifier.height(6.dp))
//
//                }
//
//
//
//
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = product.productName,
//                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
//            )
//        }
//    }
}