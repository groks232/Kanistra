package com.groks.kanistra.feature.domain.use_case.cart

import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.domain.model.SimpleResponse
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import javax.inject.Inject

class EditCartItem @Inject constructor(
    private val repository: KanistraRepository
) {
    suspend operator fun invoke(cartItem: CartItem): SimpleResponse {
        return repository.editCartItem(cartItem)
    }
}