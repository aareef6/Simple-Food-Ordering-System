package com.example.androidmainassessment3.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.androidmainassessment3.R
import com.example.androidmainassessment3.repository.ProductRepo
import com.example.androidmainassessment3.repository.ProfileRepo
import com.example.androidmainassessment3.room.FoodDB
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.room.model.Profile
import com.example.androidmainassessment3.ui.theme.PriceBatchColor
import com.example.androidmainassessment3.ui.theme.PriceTextColor
import com.example.androidmainassessment3.viewmodel.factory.ProductViewModelFactory
import com.example.androidmainassessment3.viewmodel.factory.ProfileViewModelFactory
import com.example.androidmainassessment3.viewmodel.model.ProductViewModel
import com.example.androidmainassessment3.viewmodel.model.ProfileViewModel


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.KeyboardArrowDown

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.androidmainassessment3.commoncomposable.dialoges.ProductDeletingAlertDialog
import com.example.androidmainassessment3.commoncomposable.dialoges.ProductUpdatingAlertDialog
import com.example.androidmainassessment3.ui.theme.AndroidMainAssessment3Theme
import com.example.androidmainassessment3.ui.theme.ButtonColor
import com.example.androidmainassessment3.ui.theme.CardBack
import com.example.androidmainassessment3.ui.theme.PrimaryGreen


class AdminProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var database= FoodDB.getInstance(applicationContext)

            var profilerepo = ProfileRepo(database.profileDao)
            var productRepo = ProductRepo(database.productDao)

            var profileViewModel: ProfileViewModel= ViewModelProvider(this, ProfileViewModelFactory(profilerepo)).get(ProfileViewModel::class.java)
            var productViewModel: ProductViewModel= ViewModelProvider(this, ProductViewModelFactory(productRepo)).get(ProductViewModel::class.java)

            var updatingDialogState = remember { mutableStateOf(false) }
            var deletingDialogState = remember { mutableStateOf(false) }
            var imageGalleryId = remember { mutableStateOf(0) }

            AndroidMainAssessment3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductListScreen(
                        productViewModel,
                        modifier = Modifier.padding(innerPadding),
                        imageGalleryId,
                        updatingDialogState,
                        deletingDialogState
                    )
                    if(updatingDialogState.value)
                    {
                        ProductUpdatingAlertDialog(updatingDialogState,productViewModel,imageGalleryId)
                    }

                    if(deletingDialogState.value)
                    {
                        ProductDeletingAlertDialog(deletingDialogState,productViewModel,imageGalleryId)
                        {
                            productViewModel.deleteSingleTaskData(imageGalleryId.value)
                            deletingDialogState.value = false
                        }
                    }
                }
            }
        }
    }
}

// Theme Colors
val GreenPrimary = Color(0xFF006633)
val LightPeach = Color(0xFFF8E0B0)
val TextGray = Color(0xFF8A8A8A)



@Composable
fun ProductListScreen(productViewModel: ProductViewModel, modifier: Modifier = Modifier,imageGalleryId: MutableState<Int>,
                      updatingDialogState: MutableState<Boolean>,deletingDialogState: MutableState<Boolean>) {
    val foodData by productViewModel.getProductVM.observeAsState(emptyList())
    var context= LocalContext.current
    var activity=context as Activity
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp, start = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { activity.finish() },
                modifier = Modifier
                    .background(Color(0xFFEFEFEF), shape = CircleShape)
                    .size(40.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = GreenPrimary)
            }

            Button(
                onClick = {
                    context.startActivity(
                        Intent(context, AdminAddProductActivity::class.java)
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "+  Add Product", color = Color.White)
            }

            IconButton(onClick = { }) {
                Icon(Icons.Default.FilterList, contentDescription = "Filter")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if(foodData.isEmpty()){
            Box(modifier= Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Icon(Icons.Default.HourglassEmpty,"empty", Modifier.size(200.dp) , tint = TextGray)
            }
        }else{
            LazyColumn(
                modifier= Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items (foodData) { product ->
                    ProductCard(product,imageGalleryId, updatingDialogState,
                        deletingDialogState)
                }
            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductCard(
    product: Product,
    imageGalleryId: MutableState<Int>,
    updatingDialogState: MutableState<Boolean>,
    deletingDialogState: MutableState<Boolean>
) {
    var expanded by remember { mutableStateOf(false) }

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

                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                                contentDescription = "Expand",
                                tint = Color(0xFF666666)
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(visible = expanded) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            imageGalleryId.value = product.id!!
                            updatingDialogState.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6BFF9C)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Edit", color = Color.Black)
                    }

                    Button(
                        onClick = {
                            imageGalleryId.value = product.id!!
                            deletingDialogState.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFE6E6)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            "Delete",
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    AndroidMainAssessment3Theme {
        Greeting2("Android")
    }
}