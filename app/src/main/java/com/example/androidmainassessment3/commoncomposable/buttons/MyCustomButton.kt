package com.example.androidmainassessment3.commoncomposable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidmainassessment3.R
import com.example.androidmainassessment3.ui.theme.ButtonColor


@Composable
fun CustomButton(clickEvent:()-> Unit,modifier: Modifier,text: String){
    Button(
        onClick = { clickEvent() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonColor
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
    ) {
        Text(
            text = "$text",
            color = colorResource(R.color.white), // same as android:textColor="@color/white"
            fontSize = 15.sp,    // same as android:textSize="15dp"
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}