package com.groks.kanistra.feature.presentation.shippings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ShippingsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Shippings", modifier = Modifier.align(Alignment.Center))
    }
}