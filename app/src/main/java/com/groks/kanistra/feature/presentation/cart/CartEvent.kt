package com.groks.kanistra.feature.presentation.cart

import com.groks.kanistra.feature.domain.model.CartItem

sealed class CartEvent {
    data class DeleteCartItem(val cartItem: CartItem): CartEvent()
    object RestoreCartItem: CartEvent()
}
