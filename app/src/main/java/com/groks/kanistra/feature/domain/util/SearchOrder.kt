package com.groks.kanistra.feature.domain.util

sealed class SearchOrder(val orderType: OrderType) {
    class Price(orderType: OrderType): SearchOrder(orderType)
    class DeliveryDate(orderType: OrderType): SearchOrder(orderType)

    fun copy(orderType: OrderType): SearchOrder {
        return when (this){
            is Price -> Price(orderType)
            is DeliveryDate -> DeliveryDate(orderType)
        }
    }
}