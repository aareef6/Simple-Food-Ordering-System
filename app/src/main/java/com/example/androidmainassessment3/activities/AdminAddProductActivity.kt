package com.example.androidmainassessment3.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.ViewModelProvider
import com.example.androidmainassessment3.R
import com.example.androidmainassessment3.repository.ProductRepo
import com.example.androidmainassessment3.repository.ProfileRepo
import com.example.androidmainassessment3.room.FoodDB
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.room.model.Profile
import com.example.androidmainassessment3.ui.theme.AndroidMainAssessment3Theme
import com.example.androidmainassessment3.ui.theme.ButtonColor
import com.example.androidmainassessment3.ui.theme.PrimaryGreen
import com.example.androidmainassessment3.viewmodel.factory.ProductViewModelFactory
import com.example.androidmainassessment3.viewmodel.factory.ProfileViewModelFactory
import com.example.androidmainassessment3.viewmodel.model.ProductViewModel
import com.example.androidmainassessment3.viewmodel.model.ProfileViewModel

class AdminAddProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var database= FoodDB.getInstance(applicationContext)

        var profilerepo = ProfileRepo(database.profileDao)
        var productRepo = ProductRepo(database.productDao)

        var profileViewModel: ProfileViewModel= ViewModelProvider(this, ProfileViewModelFactory(profilerepo)).get(ProfileViewModel::class.java)
        var productViewModel: ProductViewModel= ViewModelProvider(this, ProductViewModelFactory(productRepo)).get(ProductViewModel::class.java)
        setContent {


            AndroidMainAssessment3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AddProduct(
                        productViewModel,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun AddProduct(
    productViewModel: ProductViewModel,
    modifier: Modifier
) {
    var context= LocalContext.current
    var activity=context as Activity
    var productName by remember { mutableStateOf("") }
    var catogory by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var cExpanded by remember { mutableStateOf(false) }

    val catogorys = listOf("Beverages", "Foods","Juice","Snacks","Fast Food")
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    var nameisError by remember { mutableStateOf(false) }
    var categoryisError by remember { mutableStateOf(false) }
    var descisError by remember { mutableStateOf(false) }
    var priceisError by remember { mutableStateOf(false) }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            try {
                context.contentResolver.takePersistableUriPermission(it, flags)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
            selectedImageUri = it
        }
    }

    val icon = if (cExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { activity.finish() },
                modifier = Modifier
                    .background(Color(0xFFEFEFEF), shape = CircleShape)
                    .size(40.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = GreenPrimary)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo",
                tint = PrimaryGreen,
                modifier = Modifier.size(23.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Title
        Text(
            text = "Add New Products",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Share your recipe, spread the joy.",
            fontSize = 14.sp,
            color = TextGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { imageLauncher.launch(arrayOf("image/*")) },
            colors = ButtonDefaults.buttonColors(PrimaryGreen),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Pick Food Image", color = Color.White)
        }

        selectedImageUri?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Image Selected ✔",
                color = PrimaryGreen,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            leadingIcon = {
                Icon(Icons.Default.Fastfood,
                    contentDescription = null,
                    tint = PrimaryGreen)
            },
            placeholder = { Text("Food Name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (nameisError) {
                    Text(
                        text = "Minimum 3 characters required",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(3.dp))

//        OutlinedTextField(
//            value = catogory,
//            onValueChange = { catogory = it },
//            modifier = Modifier
//                .fillMaxWidth()
//                .onGloballyPositioned { coordinates ->
//                    mTextFieldSize = coordinates.size.toSize()
//                },
//            label = {Text(if(catogory.isEmpty())"-- Select Catogory -- " else "-- Selected Catogory -- ", color = Color.Black)},
//            trailingIcon = {
//                Icon(icon,"contentDescription",
//                    Modifier.clickable { cExpanded = !cExpanded })
//            },
//            supportingText = {
//                if (categoryisError) {
//                    Text(
//                        text = "Select Any One",
//                        color = MaterialTheme.colorScheme.error
//                    )
//                }
//            }
//        )

        OutlinedTextField(
            value = catogory,
            onValueChange = { catogory = it },
            leadingIcon = {
                Icon(Icons.Default.Category,
                    contentDescription = null,
                    tint = PrimaryGreen)
            },
            placeholder = {Text(if(catogory.isEmpty())"-- Select Catogory -- " else "-- Selected Catogory -- ", color = Color.Black)},
            singleLine = true,
            readOnly = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth().onGloballyPositioned { coordinates ->
                mTextFieldSize = coordinates.size.toSize()
            },
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { cExpanded = !cExpanded })
            },
            supportingText = {
                if (categoryisError) {
                    Text(
                        text = "Minimum 3 characters required",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        DropdownMenu(
            expanded = cExpanded,
            onDismissRequest = { cExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
        ) {
            catogorys.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label) },
                    onClick = {
                        catogory=label
                        cExpanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(3.dp))

        OutlinedTextField(
            value = desc,
            onValueChange = { desc = it },
            leadingIcon = {
                Icon(Icons.Default.Description,
                    contentDescription = null,
                    tint = PrimaryGreen)
            },
            placeholder = { Text("Description") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth().height(100.dp),
            supportingText = {
                if (descisError) {
                    Text(
                        text = "Minimum 3 characters required",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(3.dp))

        OutlinedTextField(
            value = price,
            onValueChange ={input->

                if (input.all { it.isDigit() }) {
                    price = input
                }
            },
            leadingIcon = {
                Icon(Icons.Default.CurrencyRupee,
                    contentDescription = null,
                    tint = PrimaryGreen)
            },
            placeholder = { Text("Price") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (priceisError) {
                    Text(
                        text = "Minimum 3 to 8 Numbers required",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                //User name
                if(productName.length < 3) {
                    Log.d("tag"," If userName.length <= 4 ")
                    nameisError = true
                } else{
                    Log.d("tag","else userName.length <= 4")
                    nameisError = false
                }

                if(catogory.length < 3) {
                    Log.d("tag"," If userName.length <= 4 ")
                    categoryisError = true
                } else{
                    Log.d("tag","else userName.length <= 4")
                    categoryisError = false
                }

                if(desc.length < 3) {
                    Log.d("tag"," If userName.length <= 4 ")
                    descisError = true
                } else{
                    Log.d("tag","else userName.length <= 4")
                    descisError = false

                }

                if(price.length < 2) {
                    Log.d("tag"," If userName.length <= 4 ")
                    priceisError = true
                } else{
                    Log.d("tag","else userName.length <= 4")
                    priceisError = false
                }

                if (productName.length >= 3 && catogory.length >= 3 && desc.length >= 3 && price.length >= 2) {
                    nameisError = false
                    categoryisError = false
                    priceisError=false
                    descisError=false

                    var productData=Product(image=selectedImageUri.toString(), productName = productName, catogory = catogory, desc = desc, Price = price)
                    productViewModel.insertProduct(productData)

                    productName=""
                    desc=""
                    price=""
                    catogory=""
                    selectedImageUri=null

                    Toast.makeText(context,"Food Inserted Successfully", Toast.LENGTH_SHORT).show()
                    activity.finish()


                }


            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(PrimaryGreen),
            shape = RoundedCornerShape(12.dp),
        ) {
            Text("Add", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))


    }
}
