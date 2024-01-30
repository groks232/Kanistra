package com.groks.kanistra.feature.presentation.favorites

import com.groks.kanistra.feature.domain.model.FavoritesItem

sealed class FavoritesEvent {
    data class DeleteFavoritesItem(val favoritesItem: FavoritesItem): FavoritesEvent()
    object GetFavorites: FavoritesEvent()
    object RestoreFavoritesItem: FavoritesEvent()
    object RefreshFavorites: FavoritesEvent()
}