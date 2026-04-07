package com.example.androidmainassessment3.commoncomposable.dialoges

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.androidmainassessment3.room.model.Product
import com.example.androidmainassessment3.ui.theme.PrimaryGreen
import com.example.androidmainassessment3.viewmodel.model.ProductViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductUpdatingAlertDialog(updatedialogState: MutableState<Boolean>,
                             vModel: ProductViewModel,imageGalleryId: MutableState<Int>) {
    val buttonColor = PrimaryGreen

    var title by remember { mutableStateOf("") }
    var catogory by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val catogorys = listOf("Lunch", "Dinner")
    var cExpanded by remember { mutableStateOf(false) }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    // Launcher to pick image
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    val icon = if (cExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var singleImageData=vModel.getSingleProductData(imageGalleryId.value).observeAsState()
    Log.d("singleImageData",imageGalleryId.value.toString())

    title=singleImageData.value?.productName.toString()
    description=singleImageData.value?.desc.toString()
    catogory=singleImageData.value?.catogory.toString()
    price=singleImageData.value?.Price.toString()
    selectedImageUri=singleImageData.value?.image?.toUri()

    var nameisError by remember { mutableStateOf(false) }
    var categoryisError by remember { mutableStateOf(false) }
    var descisError by remember { mutableStateOf(false) }
    var priceisError by remember { mutableStateOf(false) }



    AlertDialog(
        onDismissRequest = { updatedialogState.value = false },
        confirmButton = {
            Button(
                onClick = {
                    var product= Product(id =imageGalleryId.value , productName =title, desc = description, image = selectedImageUri.toString(), Price = price, catogory = catogory)
                    vModel.updateProduct(
                        product
                    )
                    updatedialogState.value = false

                },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.width(140.dp)
            ) {
                Text("Update", color = Color.White)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { updatedialogState.value = false },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.width(120.dp)
            ) {
                Text("Cancel")
            }
        },
        title = {
            Text(
                "Update Record",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = buttonColor
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {


                Card (
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),

                ) {

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Log.d("singleImageData",selectedImageUri.toString())
                        GlideImage(
                            model =selectedImageUri,
                            modifier = Modifier
                                .height(150.dp)
                                .width(150.dp).padding(10.dp),
                            contentDescription = "lskdj")

                        Button(
                            onClick = { imageLauncher.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(top = 10.dp, end = 10.dp)
                        ) {
                            Text("Update Image", color = Color.White)
                        }
                    }



                }




                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Food Name") },
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

//                OutlinedTextField(
//                    value = catogory,
//                    onValueChange = { catogory = it },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .onGloballyPositioned { coordinates ->
//                            mTextFieldSize = coordinates.size.toSize()
//                        },
//                    label = {Text(if(catogory.isEmpty())"-- Select Catogory -- " else "-- Selected Catogory -- ", color = Color.Black)},
//                    trailingIcon = {
//                        Icon(icon,"contentDescription",
//                            Modifier.clickable { cExpanded = !cExpanded })
//                    },
//                    supportingText = {
//                        if (categoryisError) {
//                            Text(
//                                text = "Minimum 3 characters required",
//                                color = MaterialTheme.colorScheme.error
//                            )
//                        }
//                    }
//                )
//                DropdownMenu(
//                    expanded = cExpanded,
//                    onDismissRequest = { cExpanded = false },
//                    modifier = Modifier
//                        .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
//                ) {
//                    catogorys.forEach { label ->
//                        DropdownMenuItem(
//                            text = { Text(text = label) },
//                            onClick = {
//                                catogory=label
//                                cExpanded = false
//                            }
//                        )
//                    }
//                }

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    supportingText = {
                        if (descisError) {
                            Text(
                                text = "Minimum 3 characters required",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = {
                        if (priceisError) {
                            Text(
                                text = "Minimum 3 characters required",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

//                Button(
//                    onClick = {
//                        var imageGallery= ImageGallery(id =imageGalleryId.value , title=title, description = description, image = selectedImageUri.toString())
//                        vModel.updatetImageGallery(
//                            imageGallery
//                        )
//                        updatedialogState.value = false
//
//                    },
//                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
//                    shape = RoundedCornerShape(12.dp),
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                ) {
//                    Text("Update", color = Color.White)
//                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White
    )
}