package com.example.androidmainassessment3.commoncomposable


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidmainassessment3.R
import com.example.androidmainassessment3.ui.theme.DarkBlue


@Composable
fun CustomOutlinedPasswordTextField(value: String, onValueChange:(String)-> Unit, label: @Composable ()-> Unit, supportingText: @Composable ()-> Unit,
                                    isPasswordVisible : MutableState<Boolean>
) {


    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(value) } ,
        label = { label },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor =DarkBlue,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = DarkBlue,
            cursorColor = DarkBlue
        ),
        trailingIcon = {
            val icon = if (isPasswordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff
            IconButton(onClick = { isPasswordVisible.value = !isPasswordVisible.value }) {
                Icon(icon, contentDescription = "Toggle Password Visibility")
            }
        },
        visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .padding(start = 6.dp, end = 6.dp),
        supportingText = { supportingText }
    )
}
