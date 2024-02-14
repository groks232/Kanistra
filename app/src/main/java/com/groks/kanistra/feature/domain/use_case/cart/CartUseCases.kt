package com.groks.kanistra.feature.domain.use_case.cart

data class CartUseCases(
    val addToCart: AddToCart,
    val deleteCartItem: DeleteCartItem,
    val editCartItem: EditCartItem,
    val getCart: GetCart,
    val getMultipleCartItems: GetMultipleCartItems
)