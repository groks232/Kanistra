package com.groks.kanistra.feature.presentation.returns

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ReturnsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Returns", modifier = Modifier.align(Alignment.Center))
    }
}