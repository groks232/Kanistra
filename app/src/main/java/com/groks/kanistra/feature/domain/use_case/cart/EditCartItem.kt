package com.groks.kanistra.feature.domain.use_case.cart

import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class EditCartItem @Inject constructor(
    private val repository: KanistraRepository
) {
    suspend operator fun invoke(cartItem: CartItem): ResponseBody {
        return repository.editCartItem(cartItem)
    }
}