package com.example.androidmainassessment3.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidmainassessment3.commoncomposable.CustomButton
import com.example.androidmainassessment3.ui.theme.AndroidMainAssessment3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidMainAssessment3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    mainPage(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun mainPage( modifier: Modifier = Modifier) {
    var context= LocalContext.current
    Column {
        CustomButton(clickEvent = {
            var intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        },modifier=modifier, text = "Sign In")

        Spacer(modifier.height(50.dp))

        CustomButton(clickEvent = {
            var intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        },modifier=modifier, text = "Sign Up")
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    AndroidMainAssessment3Theme {
//        Greeting("Android")
//    }
//}