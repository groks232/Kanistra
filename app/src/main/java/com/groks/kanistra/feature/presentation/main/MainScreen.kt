package com.groks.kanistra.feature.presentation.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.groks.kanistra.feature.presentation.auth.AuthScreen
import com.groks.kanistra.feature.presentation.cart.CartScreen
import com.groks.kanistra.feature.presentation.favorites.FavoritesScreen
import com.groks.kanistra.feature.presentation.main.components.BottomNavigationBar
import com.groks.kanistra.feature.presentation.order.OrderScreen
import com.groks.kanistra.feature.presentation.part_details.PartScreen
import com.groks.kanistra.feature.presentation.profile.ProfileScreen
import com.groks.kanistra.feature.presentation.register.RegisterScreen
import com.groks.kanistra.feature.presentation.search.SearchScreen
import com.groks.kanistra.feature.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHostEntry() {
    val navController = rememberNavController()

    var isBottomNavVisible by remember { mutableStateOf(true) }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomNavVisible
            ) {
                BottomNavigationBar(
                    navController = navController,
                    modifier = Modifier.height(65.dp).windowInsetsPadding(WindowInsets.safeDrawing)/*.animateContentSize()*/
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
                val coroutineScope = rememberCoroutineScope()
                val modalSheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true
                )
                var showBottomSheet by remember { mutableStateOf(true) }

                val scaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = SheetState(
                        skipPartiallyExpanded = false, // pass false here
                        density = LocalDensity.current,
                        initialValue = SheetValue.Expanded
                    )
                )


                BottomSheetScaffold(
                    sheetMaxWidth = Dp.Unspecified,
                    sheetShape = RoundedCornerShape(10),
                    scaffoldState = scaffoldState,
                    sheetContent = {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(128.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Swipe up to expand sheet")
                        }

                        // sheet content in the expanded state
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(64.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            for (i in 0..20) Text("Sheet content")

                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        scaffoldState.bottomSheetState.hide()
                                    }
                                }
                            ) {
                                Text(text = "Click to collapse sheet")
                            }
                        }
                    }
                ) { paddingValues ->
                    ProfileScreen(
                        onNavigateToLoginScreen = {
                            navController.navigate(Screen.AuthScreen.route)
                        }
                    )
                    isBottomNavVisible = true
                }
            }
            composable(route = Screen.PartDetails.route + "/{provider}/{id}") {
                PartScreen(navController = navController)
                isBottomNavVisible = true
            }
            composable(route = Screen.OrderScreen.route + "/{ids}") {
                OrderScreen(navController = navController)
                isBottomNavVisible = false
            }
            composable(route = Screen.AuthScreen.route) {
                AuthScreen(
                    onLogin = {
                        navController.navigate(Screen.SearchScreen.route) {
                            popUpTo(Screen.SearchScreen.route) {
                                inclusive = true
                            }
                        }
                        /*val a = navController.popBackStack(Screen.SearchScreen.route, false)
                        Log.d("POP UP", a.toString())*/
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