package com.groks.kanistra.feature.presentation.main

data class MainState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false
)