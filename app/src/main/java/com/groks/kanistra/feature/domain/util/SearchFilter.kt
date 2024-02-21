package com.groks.kanistra.feature.domain.util

sealed class SearchFilter(val minimal: Int?, val maximal: Int?) {
    class Price(minPrice: Int?, maxPrice: Int?): SearchFilter(minPrice, maxPrice)
    class DeliveryDate(minDeliveryDate: Int?, maxDeliveryDate: Int?): SearchFilter(minDeliveryDate, maxDeliveryDate)

    fun copy(minimal: Int?, maximal: Int?): SearchFilter {
        return when (this){
            is SearchFilter.Price -> SearchFilter.Price(minimal, maximal)
            is SearchFilter.DeliveryDate -> SearchFilter.DeliveryDate(minimal, maximal)
        }
    }
}