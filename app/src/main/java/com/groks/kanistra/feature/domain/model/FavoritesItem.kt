package com.groks.kanistra.feature.domain.model

data class FavoritesItem(
    val id: String,
    val partId: String,
    val title: String,
    val brand: String,
    val price: Double,
    val deliveryTime: Int,
    val image: String?,
    val provider: String,
    val creationDate: String?,
    val isInCart: Boolean? = null
)
