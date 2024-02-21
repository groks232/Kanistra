package com.groks.kanistra.feature.presentation.search

import com.groks.kanistra.feature.domain.model.Part
import com.groks.kanistra.feature.domain.util.OrderType
import com.groks.kanistra.feature.domain.util.SearchFilter
import com.groks.kanistra.feature.domain.util.SearchOrder

data class SearchState(
    val isLoading: Boolean = false,
    val partList: List<Part> = emptyList(),
    val modifiedPartList: List<Part> = emptyList(),
    val error: String = "",
    val searchOrder: SearchOrder = SearchOrder.Price(OrderType.Ascending),
    val searchFilter: SearchFilter = SearchFilter.Price(minPrice = null, maxPrice = null),
    val isOrderSectionVisible: Boolean = false,
    val isFilterSectionVisible: Boolean = false
)
