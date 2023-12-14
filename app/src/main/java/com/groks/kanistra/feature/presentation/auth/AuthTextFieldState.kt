package com.groks.kanistra.feature.presentation.auth

data class AuthTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
