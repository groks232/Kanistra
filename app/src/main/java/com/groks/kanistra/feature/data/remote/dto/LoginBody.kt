package com.groks.kanistra.feature.data.remote.dto

data class LoginBody(
    val phoneNumber: String,
    val password: String
)

class InvalidLoginDataException(message: String): Exception(message)