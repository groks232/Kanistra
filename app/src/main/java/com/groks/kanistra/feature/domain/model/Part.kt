package com.groks.kanistra.feature.domain.model

data class Part(
    val id: Int,
    val partId: String,
    val title: String,
    val brand: String,
    val price: Double,
    val deliveryTime: Int,
    val images: List<String>,
    val amount: Int,
    val isInStock: Boolean? = null,
    val provider: String
)
