package com.groks.kanistra.feature.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.groks.kanistra.common.ViewState
import com.groks.kanistra.feature.presentation.auth.AuthScreen
import com.groks.kanistra.feature.presentation.util.Screen

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
){
    val viewState by viewModel.viewState.collectAsState()
    val navController = rememberNavController()


    when(viewState){
        is ViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        is ViewState.LoggedIn -> {
            NavHost(
                navController = navController,
                startDestination = Screen.AuthScreen.route
            ){
                composable(route = Screen.AuthScreen.route) {
                    Box(modifier = Modifier.fillMaxSize()){
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "You are logged in"
                        )
                    }
                }
            }
        }
        is ViewState.NotLoggedIn -> AuthScreen()
    }
}