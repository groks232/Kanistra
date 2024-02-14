package com.groks.kanistra.feature.presentation.order

sealed class OrderEvent {
    data class GetMultipleCartItems(val ids: String): OrderEvent()
    object AgreeToRules: OrderEvent()

}