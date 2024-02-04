package com.groks.kanistra.feature.domain.util

sealed class SearchFilter(val searchOrder: SearchOrder) {
    class Price(searchOrder: SearchOrder): SearchFilter(searchOrder)
}