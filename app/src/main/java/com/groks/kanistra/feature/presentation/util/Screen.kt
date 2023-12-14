package com.groks.kanistra.feature.presentation.util

sealed class Screen(val route: String) {
    object AuthScreen: Screen("auth_screen_route")
}
