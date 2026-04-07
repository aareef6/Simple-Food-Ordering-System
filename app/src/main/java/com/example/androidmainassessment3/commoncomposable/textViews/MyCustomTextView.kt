package com.example.androidmainassessment3.commoncomposable


import android.R.style
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit


@Composable
fun CustomText(
    name: String,
    modifier: Modifier,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    textAlign: TextAlign? = null,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,

) {
    Text(
        text = "$name",
        modifier = modifier,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = FontFamily.Serif,

        textAlign = textAlign,
        color = color,
        style = style
    )
}