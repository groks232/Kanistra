package com.groks.kanistra.feature.presentation.register

sealed class RegisterEvent {
    data class EnteredName(val value: String) : RegisterEvent()
    data class EnteredEmail(val value: String) : RegisterEvent()
    data class EnteredPhoneNumber(val value: String) : RegisterEvent()
    data class EnteredPassword(val value: String) : RegisterEvent()
    object Register : RegisterEvent()
}