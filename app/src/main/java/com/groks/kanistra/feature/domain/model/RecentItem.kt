package com.groks.kanistra.feature.domain.model

data class RecentItem(
    val id: String,
    val partId: String,
    val title: String,
    val brand: String,
    val price: Double,
    val image: String?,
    val provider: String,
    val creationDate: String?,
    val isInCart: Boolean? = null
)
