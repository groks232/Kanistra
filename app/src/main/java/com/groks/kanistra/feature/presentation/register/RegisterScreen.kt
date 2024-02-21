package com.groks.kanistra.feature.presentation.register

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.groks.kanistra.feature.presentation.auth.components.PhoneField
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegister: () -> Unit = {}
    ) {
    val state = viewModel.state.value
    val passwordFieldState = viewModel.passwordFieldText.value
    val nameFieldState = viewModel.nameFieldText.value
    val emailFieldState = viewModel.emailFieldText.value
    val phoneFieldState = viewModel.phoneFieldText.value
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is RegisterViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is RegisterViewModel.UiEvent.Register -> {
                    onRegister()
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
                            .height(100.dp))
                        Text(text = "Добро пожаловать!", fontSize = 40.sp, lineHeight = 45.sp)
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(15.dp))
                        Text(text = "Создайте новый аккаунт", fontSize = 20.sp)

                    }


                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 20.dp)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = nameFieldState.text,
                            onValueChange = {
                                viewModel.onEvent(RegisterEvent.EnteredName(it))
                            },
                            shape = RoundedCornerShape(10.dp),
                            label = {
                                Text(text = "Name")
                            },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = emailFieldState.text,
                            onValueChange = {
                                viewModel.onEvent(RegisterEvent.EnteredEmail(it))
                            },
                            shape = RoundedCornerShape(10.dp),
                            label = {
                                Text(text = "Email")
                            },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                        )

                        PhoneField(
                            phone = phoneFieldState.text,
                            onPhoneChanged = { viewModel.onEvent(RegisterEvent.EnteredPhoneNumber(it)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            value = passwordFieldState.text,
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
                                viewModel.onEvent(RegisterEvent.EnteredPassword(it))
                            },
                            label = {
                                Text(text = "Password")
                            },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Go),
                            keyboardActions = KeyboardActions(
                                onGo = {
                                    viewModel.onEvent(RegisterEvent.Register)
                                }
                            )
                        )

                        Button(
                            onClick = {
                                viewModel.onEvent(RegisterEvent.Register)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(text = "Зарегистрироваться",
                                fontSize = 20.sp)
                        }

                        Text(
                            text = "Войти",
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