package com.example.androidmainassessment3.commoncomposable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay

@Composable
fun CustomImage(image: Int,modifier: Modifier= Modifier,scale: ContentScale = ContentScale.Fit) {
        Image(
            painterResource(image),
            contentDescription = "Image",
            modifier=modifier,
            contentScale = scale
        )
}