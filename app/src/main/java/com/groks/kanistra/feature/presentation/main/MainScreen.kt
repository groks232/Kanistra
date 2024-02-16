package com.groks.kanistra.feature.presentation.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.groks.kanistra.common.ViewState
import com.groks.kanistra.feature.presentation.auth.AuthScreen
import com.groks.kanistra.feature.presentation.cart.CartScreen
import com.groks.kanistra.feature.presentation.favorites.FavoritesScreen
import com.groks.kanistra.feature.presentation.main.components.BottomNavigationBar
import com.groks.kanistra.feature.presentation.order.OrderScreen
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHostEntry(){
    val navController = rememberNavController()

    var isBottomNavVisible by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomNavVisible
            ) {
                BottomNavigationBar(navController = navController, modifier = Modifier.height(60.dp)/*.animateContentSize()*/)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = /*Screen.SearchScreen.route*/"main",
            modifier = Modifier.padding(paddingValues)
        ){
            navigation(Screen.SearchScreen.route, "main") {
                composable(route = Screen.CartScreen.route) {
                    CartScreen(navController = navController)
                    isBottomNavVisible = true
                }
                composable(route = Screen.SearchScreen.route){
                    SearchScreen(navController = navController)
                    isBottomNavVisible = true
                }
                composable(route = Screen.ProfileScreen.route){
                    ProfileScreen()
                    isBottomNavVisible = true
                }
                composable(route = Screen.FavoritesScreen.route){
                    FavoritesScreen(navController = navController)
                    isBottomNavVisible = true
                }
            }
            composable(route = Screen.PartDetails.route + "/{provider}/{id}"){
                PartScreen(navController = navController)
                isBottomNavVisible = false
            }
            composable(route = Screen.OrderScreen.route + "/{ids}") {
                OrderScreen(navController = navController)
                isBottomNavVisible = false
            }
        }
    }

    /*NavHost(
        navController = navController,
        startDestination = *//*Screen.SearchScreen.route*//*"main"
    ){
        navigation(Screen.SearchScreen.route, "main") {
            composable(route = Screen.CartScreen.route) {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { paddingValues ->
                    CartScreen(navController = navController, paddingValues = paddingValues)
                }
            }
            composable(route = Screen.SearchScreen.route){
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { paddingValues ->
                    SearchScreen(navController = navController, paddingValues = paddingValues)
                }
            }
            composable(route = Screen.ProfileScreen.route){
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { paddingValues ->
                    ProfileScreen(paddingValues = paddingValues)
                }
            }
            composable(route = Screen.FavoritesScreen.route){
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    }
                ) { paddingValues ->
                    FavoritesScreen(navController = navController, paddingValues = paddingValues)
                }
            }
        }
        composable(route = Screen.PartDetails.route + "/{provider}/{id}"){
            PartScreen(navController = navController)
        }
        composable(route = Screen.OrderScreen.route + "/{ids}") {
            OrderScreen(navController = navController)
        }
    }*/

    /*NavHost(
        navController = navController,
        startDestination = Screen.SearchScreen.route
    ){
        navigation("main", "main") {
            Scaffold() {
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
            }
        }
        composable(route = Screen.PartDetails.route + "/{provider}/{id}"){
            PartScreen(navController = navController)
        }
        composable(route = Screen.OrderScreen.route + "/{ids}") {
            OrderScreen(navController = navController)
        }
    }*/
}