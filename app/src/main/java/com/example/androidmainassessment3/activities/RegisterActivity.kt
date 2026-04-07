package com.example.androidmainassessment3.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.androidmainassessment3.R
import com.example.androidmainassessment3.commoncomposable.CustomButton
import com.example.androidmainassessment3.commoncomposable.CustomImage
import com.example.androidmainassessment3.commoncomposable.CustomOutlinedPasswordTextField
import com.example.androidmainassessment3.commoncomposable.CustomOutlinedTextField
import com.example.androidmainassessment3.commoncomposable.CustomText
import com.example.androidmainassessment3.commoncomposable.textbutton.CustomTextButton
import com.example.androidmainassessment3.helpers.SharedPrefHelper
import com.example.androidmainassessment3.repository.ProfileRepo
import com.example.androidmainassessment3.room.FoodDB
import com.example.androidmainassessment3.room.model.Profile
import com.example.androidmainassessment3.ui.theme.AndroidMainAssessment3Theme
import com.example.androidmainassessment3.ui.theme.CardBack
import com.example.androidmainassessment3.ui.theme.PrimaryGreen
import com.example.androidmainassessment3.viewmodel.factory.ProfileViewModelFactory
import com.example.androidmainassessment3.viewmodel.model.ProfileViewModel
import java.util.regex.Pattern

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var database= FoodDB.getInstance(applicationContext)
        var profilerepo = ProfileRepo(database.profileDao)
        var profileViewModel: ProfileViewModel= ViewModelProvider(this, ProfileViewModelFactory(profilerepo)).get(ProfileViewModel::class.java)

        setContent {
            AndroidMainAssessment3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SignUpScreen(modifier = Modifier.padding(innerPadding),profileViewModel)
                }
            }
        }
    }
}


@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,profileViewModel: ProfileViewModel
) {
    var context= LocalContext.current
    var activity=context as Activity
    SharedPrefHelper.init(context)

    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var userisError by remember { mutableStateOf(false) }
    var emailisError by remember { mutableStateOf(false) }
    var passisError by remember { mutableStateOf(false) }
    var passLableErrorText by remember { mutableStateOf("") }

    SharedPrefHelper.init(context)
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = {activity.finish()},
                modifier = Modifier
                    .background(Color(0xFFEFEFEF), shape = CircleShape)
                    .size(40.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = GreenPrimary)
            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "App Logo",
                    tint = PrimaryGreen,
                    modifier = Modifier.size(70.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .height(3.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFFF9C6B))
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.app_name),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Create An Account",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "From Your Kitchen to Your Heart",
            fontSize = 14.sp,
            color = TextGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            leadingIcon = {
                Icon(Icons.Default.VerifiedUser,
                    contentDescription = null,
                    tint = PrimaryGreen)
            },
            placeholder = { Text("Username") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryGreen
            ),
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (userisError) {
                    Text(
                        text = "Minimum 3 characters required",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            leadingIcon = {
                Icon(Icons.Default.Email,
                    contentDescription = null,
                    tint = PrimaryGreen)
            },
            placeholder = { Text("Email") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryGreen
            ),
            supportingText = {
                if (emailisError) {
                    Text(
                        text = "Enter Valid Email",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {input->

                if (input.all { it.isDigit() }) {
                    password = input
                }
            },
            leadingIcon = {
                Icon(Icons.Default.Key, contentDescription = null,
                    tint = PrimaryGreen)
            },
            placeholder = { Text("Password") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryGreen
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    Icon(icon, contentDescription = "Toggle Password",)
                }
            },
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (passisError) {
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

                if(userName.length < 3) {
                    Log.d("tag"," If userName.length <= 4")
                    userisError = true
                } else {
                    Log.d("tag","else userName.length <= 4")
                    userisError = false
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailisError = true
                }else{
                    emailisError = false
                }

                if( password.length !in 3..8 ) {
                    Log.d("tag","If password.length !in 3..8")
                    passLableErrorText="Enter 3–8 digits only"
                    passisError = true
                }else{
                    Log.d("tag","else password.length !in 3..8")
                    passisError = false
                }

                if (userName.length >= 3 && password.length in 3..8) {
                    userisError = false
                    passisError = false
                    emailisError= false

                    profileViewModel.checkEmail(email,{
                            onValid ->
                        if(onValid) {
                            emailisError=true
                            Toast.makeText(context,"Email is Already Presented", Toast.LENGTH_SHORT).show()
                        }else{
                            var profileData= Profile(name = userName, pass =password,email = email, isAdmin = false)
                            profileViewModel.insertProfile(profileData)
                            Toast.makeText(context,"Registered Successfully", Toast.LENGTH_SHORT).show()
                            activity.finish()
                        }
                    })
//
//                        profileViewModel.insertProfile(userName,password,{
//                                onValid ->
//                            if(onValid) {
//                                SharedPrefHelper.putString("userName", userName.toString())
//                                var intent= Intent(context, UserHomeActivity::class.java)
//                                context.startActivity(intent)
//                                Toast.makeText(context,"Login Success", Toast.LENGTH_SHORT).show()
//                            }else{
//                                userName=""
//                                password=""
//
//                                Toast.makeText(context,"User name and password is wrong", Toast.LENGTH_SHORT).show()
//                            }
//                        })
                }


            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) {
            Text("SIGN UP", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Terms and conditions
        Text(
            text = "By tapping “Sign Up” you accept our terms and condition",
            fontSize = 12.sp,
            color = TextGray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun RegisterPageContent1(modifier: Modifier = Modifier,profileViewModel: ProfileViewModel) {
    var context= LocalContext.current
    var activity=context as Activity
    SharedPrefHelper.init(context)

    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var userisError by remember { mutableStateOf(false) }
    var emailisError by remember { mutableStateOf(false) }
    var passisError by remember { mutableStateOf(false) }
    var passLableErrorText by remember { mutableStateOf("") }

    SharedPrefHelper.init(context)

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.back),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFDAD7DE).copy(alpha = 0.7f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.start_logo),
                        contentDescription = "logo",

                        )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "From your kitchen to your heart.",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Username TextField
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    leadingIcon = {
                        Icon(Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = Color(0xFF965CD7))
                    },
                    placeholder = { Text("Username") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password TextField
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    leadingIcon = {
                        Icon(Icons.Default.Key, contentDescription = null,
                            tint = Color(0xFF965CD7))
                    },
                    placeholder = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    leadingIcon = {
                        Icon(Icons.Default.Email,
                            contentDescription = null,
                            tint = Color(0xFF965CD7))
                    },
                    placeholder = { Text("Email") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Sign In Button
                Button(
                    onClick = {

                        if(userName.length < 3) {
                            Log.d("tag"," If userName.length <= 4")
                            userisError = true
                        } else {
                            Log.d("tag","else userName.length <= 4")
                            userisError = false
                        }

                        if(email.length < 3) {
                            Log.d("tag"," If userName.length <= 4 ")
                            emailisError = true
                        } else {
                            Log.d("tag","else userName.length <= 4")
                            emailisError = false
                        }

                        if( password.length !in 3..8 ) {
                            Log.d("tag","If password.length !in 3..8")
                            passLableErrorText="Enter 3–8 digits only"
                            passisError = true
                        }else{
                            Log.d("tag","else password.length !in 3..8")
                            passisError = false
                        }

                        if (userName.length >= 3 && password.length in 3..8) {
                            userisError = false
                            passisError = false
                            emailisError= false

                            profileViewModel.checkEmail(email,{
                                    onValid ->
                                if(onValid) {
                                    emailisError=true
                                    Toast.makeText(context,"Email is Already Presented", Toast.LENGTH_SHORT).show()
                                    return@checkEmail
                                }else{
                                    var profileData= Profile(name = userName, pass =password,email = email, isAdmin = false)
                                    profileViewModel.insertProfile(profileData)
                                    activity.finish()
                                }
                            })




//                        profileViewModel.insertProfile(userName,password,{
//                                onValid ->
//                            if(onValid) {
//                                SharedPrefHelper.putString("userName", userName.toString())
//                                var intent= Intent(context, UserHomeActivity::class.java)
//                                context.startActivity(intent)
//                                Toast.makeText(context,"Login Success", Toast.LENGTH_SHORT).show()
//                            }else{
//                                userName=""
//                                password=""
//
//                                Toast.makeText(context,"User name and password is wrong", Toast.LENGTH_SHORT).show()
//                            }
//                        })
                        }


                    },
                    colors = ButtonDefaults
                        .buttonColors(containerColor = Color(0xFF965CD7)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("SIGN Up", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sign Up text
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Have an account yet? ", color = Color.Gray, fontSize = 14.sp)
                    Text(
                        text = "Sign In",
                        color = Color(0xFF9C6BFF),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            activity.finish()
                            var intent= Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }


    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview2() {
//    AndroidMainAssessment3Theme {
//        Greeting2("Android")
//    }
//}