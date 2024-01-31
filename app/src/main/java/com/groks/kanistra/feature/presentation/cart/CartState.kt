package com.groks.kanistra.feature.presentation.cart

import com.groks.kanistra.feature.domain.model.CartItem

data class CartState(
    val isLoading: Boolean = false,
    val cartList: MutableList<CartItem> = mutableListOf(),
    val error: String = ""
)
