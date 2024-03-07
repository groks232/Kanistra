package com.groks.kanistra.feature.presentation.profile

import com.groks.kanistra.feature.domain.model.FavoritesItem
import com.groks.kanistra.feature.domain.model.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = "",
    val favoritesList: List<FavoritesItem> = emptyList(),
)
