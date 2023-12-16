package com.groks.kanistra.feature.presentation.auth

data class AuthState(
    val isLoading: Boolean = false,
    val error: String? = ""
)