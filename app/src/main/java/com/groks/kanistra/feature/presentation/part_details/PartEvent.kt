package com.groks.kanistra.feature.presentation.part_details

import com.groks.kanistra.feature.domain.model.CartItem

sealed class PartEvent {
    data class DeleteItemFromCart(val cartItem: CartItem): PartEvent()
    object RestoreCartItem: PartEvent()
    data class AddItemToCart(val cartItem: CartItem): PartEvent()
    data class EditItem(val cartItem: CartItem): PartEvent()
}
