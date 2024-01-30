package com.groks.kanistra.feature.presentation.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import com.groks.kanistra.feature.presentation.cart.CartScreen
import com.groks.kanistra.feature.presentation.favorites.FavoritesScreen
import com.groks.kanistra.feature.presentation.main.components.BottomNavigationBar
import com.groks.kanistra.feature.presentation.part_details.PartScreen
import com.groks.kanistra.feature.presentation.profile.ProfileScreen
import com.groks.kanistra.feature.presentation.search.SearchScreen
import com.groks.kanistra.feature.presentation.util.Screen

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
){
    val viewState by viewModel.viewState.collectAsState()


    when(viewState){
        is ViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        is ViewState.LoggedIn -> {
            NavHostEntry()
        }
        is ViewState.NotLoggedIn -> {
            AuthScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavHostEntry(){
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) {paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.SearchScreen.route,
            modifier = Modifier.padding(paddingValues)
        ){
            composable(route = Screen.CartScreen.route) {
                CartScreen(navController = navController)
            }
            composable(route = Screen.SearchScreen.route){
                SearchScreen(navController = navController)
            }
            composable(route = Screen.ProfileScreen.route){
                ProfileScreen()
            }
            composable(route = Screen.FavoritesScreen.route){
                FavoritesScreen(navController = navController)
            }
            composable(route = Screen.PartDetails.route + "/{provider}/{id}"){
                PartScreen()
            }
        }
    }
}