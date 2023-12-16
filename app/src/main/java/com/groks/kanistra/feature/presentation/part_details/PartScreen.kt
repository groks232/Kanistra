package com.groks.kanistra.feature.presentation.part_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PartScreen(
    viewModel: PartViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(text = "Part details, not implemented yet")
            Text(text = state)
        }
    }
}