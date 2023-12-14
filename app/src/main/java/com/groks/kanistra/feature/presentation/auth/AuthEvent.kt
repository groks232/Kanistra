package com.groks.kanistra.feature.presentation.auth

sealed class AuthEvent {
    data class EnteredLogin(val value: String) : AuthEvent()
    data class EnteredPassword(val value: String) : AuthEvent()
    object Login : AuthEvent()
    object ForgotPassword : AuthEvent()
}
