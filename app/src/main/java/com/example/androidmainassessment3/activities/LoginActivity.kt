package com.example.androidmainassessment3.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.colorResource
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
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
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
import com.example.androidmainassessment3.ui.theme.BackColor
import com.example.androidmainassessment3.ui.theme.CardBack
import com.example.androidmainassessment3.ui.theme.PrimaryGreen
import com.example.androidmainassessment3.viewmodel.factory.ProfileViewModelFactory
import com.example.androidmainassessment3.viewmodel.model.ProfileViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        var database= FoodDB.getInstance(applicationContext)
        var profilerepo = ProfileRepo(database.profileDao)
        var profileViewModel: ProfileViewModel= ViewModelProvider(this, ProfileViewModelFactory(profilerepo)).get(ProfileViewModel::class.java)
        var profileData= Profile(name = "tkr", pass = "123", email = "admin@gmail.com", isAdmin = true)
        profileViewModel.insertProfile(profileData)


        setContent {
            AndroidMainAssessment3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SignInScreen(modifier = Modifier.padding(innerPadding),profileViewModel)
                }
            }
        }
    }
}

@Composable
fun SignInScreen(
    modifier: Modifier,
    profileViewModel: ProfileViewModel
) {
    var context= LocalContext.current
    var activity=context as Activity
    SharedPrefHelper.init(context)

    var userName = remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var userisError by remember { mutableStateOf(false) }
    var passisError by remember { mutableStateOf(false) }
    var isPasswordVisible = remember { mutableStateOf(false) }
    var passLableErrorText by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "App Logo",
                    tint = PrimaryGreen,
                    modifier = Modifier.size(70.dp).padding(top = 20.dp)
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
            text = "Sign In",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "From your kitchen to your heart.",
            fontSize = 14.sp,
            color = TextGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            leadingIcon = {
                Icon(Icons.Default.VerifiedUser,
                    contentDescription = null,
                    tint = PrimaryGreen)
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryGreen
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next),
            placeholder = { Text("Email") },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (userisError) {
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
            onValueChange = { password = it },
            leadingIcon = {
                Icon(Icons.Default.Key, contentDescription = null,
                    tint = PrimaryGreen)
            },
            placeholder = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(Icons.Default.Visibility, contentDescription = "Toggle Password")
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = PrimaryGreen
            ),
            shape = RoundedCornerShape(12.dp),
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

        // Login Button
        Button(
            onClick = {
                if(!Patterns.EMAIL_ADDRESS.matcher(userName.value).matches()) {
                    Log.d("tag"," If userName.length <= 4 ")
                    userisError = true
                } else{
                    Log.d("tag","else userName.length <= 4")
                    userisError = false
                }

                if( password.length !in 3..8 ) {
                    Log.d("tag","If password.length !in 3..8")
                    passLableErrorText="Enter 3–8 digits only"
                    passisError = true
                }else{
                    Log.d("tag","else password.length !in 3..8")
                    passisError = false
                }

                if (userName.value.length >= 3 && password.length in 3..8) {
                    userisError = false
                    passisError = false
                    profileViewModel.checkProfile(userName.value,password,{
                            onValid ->
                        if(onValid) {
                            profileViewModel.checkisAdmin(userName.value,{ isAdmin->
                                SharedPrefHelper.putString("email", userName.value.toString())
                                SharedPrefHelper.putString("isadmin", isAdmin.toString())

                                userName.value=""
                                password=""

                                if(isAdmin){
                                    var intent= Intent(context, AdminHomeActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    context.startActivity(intent)
                                    Toast.makeText(context,"Admin Login Success", Toast.LENGTH_SHORT).show()
                                }else{
                                    var intent= Intent(context, UserHomeActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    context.startActivity(intent)
                                    Toast.makeText(context,"User Login Success", Toast.LENGTH_SHORT).show()
                                }

                            })


                        }else{
                            userName.value=""
                            password=""

                            Toast.makeText(context,"User name and password is wrong", Toast.LENGTH_SHORT).show()
                        }
                    })
                }


            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
        ) {
            Text("LOGIN", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                var intent= Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LightPeach)
        ) {
            Text("CREATE AN ACCOUNT", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}


