package com.groks.kanistra.feature.presentation.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val loginState = viewModel.loginFieldText.value
    val passwordState = viewModel.passwordFieldText.value
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is AuthViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is AuthViewModel.UiEvent.Login -> {
                    viewModel.clearState()
                }
            }
        }
    }

    Scaffold(snackbarHost = {
        SnackbarHost(snackbarHostState)
    }) {
        when(state.isLoading){
            true -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
            false -> {
                Box(modifier = Modifier.fillMaxSize()){
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)){
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp))
                        Text(text = "Welcome back!", fontSize = 40.sp)
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp))
                        Text(text = "Login below or create new account", fontSize = 20.sp)

                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp))

                        OutlinedTextField(
                            value = loginState.text,
                            onValueChange = {
                                viewModel.onEvent(AuthEvent.EnteredLogin(it))
                            },
                            Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            label = {
                                Text(text = "Email")
                            }
                        )

                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(35.dp))

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            value = passwordState.text,
                            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (passwordVisibility)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                val description = if (passwordVisibility) "Hide password" else "Show password"

                                IconButton(onClick = {passwordVisibility = !passwordVisibility}){
                                    Icon(imageVector  = image, description)
                                }
                            },
                            onValueChange = {
                                viewModel.onEvent(AuthEvent.EnteredPassword(it))
                            },
                            label = {
                                Text(text = "Password")
                            }
                        )
                    }


                    Column(modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 20.dp)) {
                        Button(
                            onClick = {
                                viewModel.onEvent(AuthEvent.Login)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(text = "Log in",
                                fontSize = 20.sp)
                        }

                        Text(
                            text = "Forgot Password",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(20.dp),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}