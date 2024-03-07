package com.groks.kanistra.feature.presentation.payment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.groks.kanistra.feature.presentation.util.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value

    if (state.someData.isNotBlank()){
        viewModel.viewModelScope.launch {
            delay(5000L)
            navController.navigate(Screen.SearchScreen.route) {
                popUpTo(Screen.SearchScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = state.someData, modifier = Modifier.align(Alignment.Center))
    }

    if(state.error.isNotBlank()) {
        Box(modifier = Modifier.fillMaxSize()){
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
    }
    if(state.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}