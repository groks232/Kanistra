package com.groks.kanistra.feature.presentation.profile

import android.annotation.SuppressLint
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Scaffold {
        when(state.isLoading) {
            true -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            false -> {
                if (state.user != null) {
                    Box(modifier = Modifier
                        .fillMaxSize(),
                        contentAlignment = Alignment.Center
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
                                    .weight(1f)
                                    .padding(bottom = 60.dp)
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
                                .padding(bottom = 100.dp)
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