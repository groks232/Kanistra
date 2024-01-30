package com.groks.kanistra.feature.domain.use_case.favorites

data class FavoritesUseCases(
    val addToFavorites: AddToFavorites,
    val deleteFavoritesItem: DeleteFavoritesItem,
    val getFavorites: GetFavorites
)
