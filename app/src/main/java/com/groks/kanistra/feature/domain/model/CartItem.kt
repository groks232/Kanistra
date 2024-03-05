package com.groks.kanistra.feature.domain.model

data class CartItem(
    val id: String? = "null",
    val provider: String,
    val partId: String,
    var amount: Int,
    val image: String,
    val title: String,
    val brand: String,
    val price: Double,
    val deliveryTime: Int?,
    val creationDate: String?,
    val isInFavorites: Boolean? = null,
    var isSelected: Boolean? = null
)