package com.groks.kanistra.feature.data.remote.dto

data class RegisterBody(
    val fullName: String,
    val phoneNumber: String,
    val emailAddress: String,
    val password: String
)
