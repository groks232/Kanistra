package com.groks.kanistra.feature.presentation.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.groks.kanistra.feature.presentation.auth.AuthScreen
import com.groks.kanistra.feature.presentation.cart.CartScreen
import com.groks.kanistra.feature.presentation.favorites.FavoritesScreen
import com.groks.kanistra.feature.presentation.main.components.BottomNavigationBar
import com.groks.kanistra.feature.presentation.order.OrderScreen
import com.groks.kanistra.feature.presentation.part_details.PartScreen
import com.groks.kanistra.feature.presentation.payment.PaymentScreen
import com.groks.kanistra.feature.presentation.payment_history.PaymentHistoryScreen
import com.groks.kanistra.feature.presentation.profile.ProfileScreen
import com.groks.kanistra.feature.presentation.purchases.PurchasesScreen
import com.groks.kanistra.feature.presentation.register.RegisterScreen
import com.groks.kanistra.feature.presentation.returns.ReturnsScreen
import com.groks.kanistra.feature.presentation.search.SearchScreen
import com.groks.kanistra.feature.presentation.shippings.ShippingsScreen
import com.groks.kanistra.feature.presentation.util.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val cartAmount by viewModel.cartAmount.collectAsState()
    //NavHostEntry(cartAmount)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHostEntry(
    viewModel: MainViewModel = hiltViewModel(),
    /*badgeCount: Int*/
) {
    val cartAmount by viewModel.cartAmount.collectAsState()

    val navController = rememberNavController()

    var isBottomNavVisible by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomNavVisible
            ) {
                BottomNavigationBar(
                    navController = navController,
                    modifier = Modifier
                        .height(65.dp)
                        .windowInsetsPadding(WindowInsets.safeDrawing),
                    badgeCount = cartAmount
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.SearchScreen.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screen.SearchScreen.route) {
                SearchScreen(
                    navController = navController,
                    onNavigateToLoginScreen = {
                        navController.navigate(Screen.AuthScreen.route)
                    }
                )
                isBottomNavVisible = true
            }
            composable(route = Screen.CartScreen.route) {
                CartScreen(navController = navController)
                isBottomNavVisible = true
            }
            composable(route = Screen.FavoritesScreen.route) {
                FavoritesScreen(navController = navController)
                isBottomNavVisible = true
            }
            composable(route = Screen.ProfileScreen.route) {
                ProfileScreen(
                    navController = navController,
                    onNavigateToLoginScreen = {
                        navController.navigate(Screen.AuthScreen.route)
                    }
                )
                isBottomNavVisible = true
            }
            composable(route = Screen.PartDetails.route + "/{provider}/{id}") {
                PartScreen(navController = navController)
                isBottomNavVisible = true
            }
            composable(route = Screen.OrderScreen.route + "/{ids}") {
                OrderScreen(navController = navController)
                isBottomNavVisible = false
            }
            composable(route = Screen.PaymentScreen.route) {
                PaymentScreen(navController = navController)
            }
            composable(route = Screen.AuthScreen.route) {
                AuthScreen(
                    onLogin = {
                        navController.navigate(Screen.SearchScreen.route) {
                            popUpTo(Screen.SearchScreen.route) {
                                inclusive = true
                            }
                        }
                    },
                    onRegisterClick = {
                        navController.navigate(Screen.RegisterScreen.route)
                    }
                )
                isBottomNavVisible = false
            }
            composable(route = Screen.RegisterScreen.route) {
                RegisterScreen(
                    onRegister = {
                        navController.navigate(Screen.SearchScreen.route) {
                            popUpTo(Screen.AuthScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(route = Screen.Shippings.route) {
                ShippingsScreen()
            }
            composable(route = Screen.Purchases.route) {
                PurchasesScreen()
            }
            composable(route = Screen.PaymentHistory.route) {
                PaymentHistoryScreen()
            }
            composable(route = Screen.Returns.route) {
                ReturnsScreen()
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