package com.groks.kanistra.feature.presentation.order

import com.groks.kanistra.feature.domain.model.CartItem

data class OrderState(
    val isLoading: Boolean = false,
    val orderList: MutableList<CartItem> = mutableListOf(),
    val error: String = "",
    val agreeToRules: Boolean = true
)
