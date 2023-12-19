package com.groks.kanistra.feature.presentation.search

import com.groks.kanistra.feature.domain.model.Part

sealed class SearchEvent {
    data class EnteredPartName(val value: String): SearchEvent()

    object Search: SearchEvent()

    data class AddToCart(val part: Part): SearchEvent()

    data class AddToFavorites(val part: Part): SearchEvent()
}