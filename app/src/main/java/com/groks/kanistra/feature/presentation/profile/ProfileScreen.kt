package com.groks.kanistra.feature.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.groks.kanistra.common.ViewState

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToLoginScreen: () -> Unit = {}
) {
    val viewState by viewModel.viewState.collectAsState()

    when(viewState){
        is ViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        is ViewState.NotLoggedIn -> {
            LaunchedEffect(true) {
                onNavigateToLoginScreen()
            }
        }
        is ViewState.LoggedIn -> {
            val state = viewModel.state.value
            Scaffold { paddingValues ->
                when(state.isLoading) {
                    true -> {
                        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                    false -> {
                        if (state.user != null) {
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(top = 100.dp)
                                        .align(alignment = Alignment.TopCenter)
                                        .height(200.dp),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Icon(
                                        Icons.Filled.AccountCircle,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .weight(0.8f)
                                    )
                                    Text(
                                        text = state.user.fullName,
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .weight(0.2f)
                                    )
                                }


                                OutlinedButton(
                                    onClick = {
                                        viewModel.logOut()
                                    },
                                    modifier = Modifier
                                        .align(alignment = Alignment.BottomCenter)
                                        .fillMaxWidth()
                                ) {
                                    Text(text = "Log out")
                                }
                            }
                        }
                        if (state.error.isNotBlank()) {
                            Text(text = state.error)
                        }
                    }
                }
            }
        }
    }
}