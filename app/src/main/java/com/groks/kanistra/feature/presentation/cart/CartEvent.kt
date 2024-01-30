package com.groks.kanistra.feature.presentation.cart

import com.groks.kanistra.feature.domain.model.CartItem

sealed class CartEvent {
    data class DeleteCartItem(val cartItem: CartItem): CartEvent()
    object GetCart: CartEvent()
    object RestoreCartItem: CartEvent()
    object RefreshCart: CartEvent()

    data class IncreaseAmount(val cartItem: CartItem): CartEvent()
    data class DecreaseAmount(val cartItem: CartItem): CartEvent()
}
