package com.groks.kanistra.feature.presentation.search

import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.domain.model.FavoritesItem
import com.groks.kanistra.feature.domain.model.Hint
import com.groks.kanistra.feature.domain.util.SearchOrder

sealed class SearchEvent {
    data class EnteredPartName(val value: String): SearchEvent()
    object Search: SearchEvent()
    data class AddToCart(val cartItem: CartItem): SearchEvent()
    data class AddToFavorites(val favoritesItem: FavoritesItem): SearchEvent()
    data class AddHint(val hint: Hint): SearchEvent()
    object GetHints: SearchEvent()
    data class DeleteHint(val hint: Hint): SearchEvent()

    data class Order(val searchOrder: SearchOrder): SearchEvent()
    object ToggleOrderSection: SearchEvent()
}