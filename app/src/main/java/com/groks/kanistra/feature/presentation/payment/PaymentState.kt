package com.groks.kanistra.feature.presentation.payment

data class PaymentState(
    val isLoading: Boolean = false,
    val someData: String = "",
    val error: String = "",
)
