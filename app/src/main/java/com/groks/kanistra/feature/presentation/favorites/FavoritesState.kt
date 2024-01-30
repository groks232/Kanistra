package com.groks.kanistra.feature.presentation.favorites

import com.groks.kanistra.feature.domain.model.FavoritesItem

data class FavoritesState(
    val isLoading: Boolean = false,
    val favoritesList: List<FavoritesItem> = emptyList(),
    val error: String = ""
)
