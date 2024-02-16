package com.groks.kanistra.feature.presentation.register

data class RegisterState(
    val isLoading: Boolean = false,
    val error: String? = ""
)