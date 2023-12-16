package com.groks.kanistra.feature.domain.use_case.auth

data class AuthUseCases(
    val login: Login,
    val register: Register,
    val forgotPassword: ForgotPassword,
    val logOut: LogOut
)