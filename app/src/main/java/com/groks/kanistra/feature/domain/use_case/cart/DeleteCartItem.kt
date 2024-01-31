package com.groks.kanistra.feature.domain.use_case.cart

import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.domain.model.SimpleResponse
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import javax.inject.Inject

class DeleteCartItem @Inject constructor(
    private val repository: KanistraRepository
) {
    suspend operator fun invoke(cartItem: CartItem): SimpleResponse {
        /*try {
            emit(Resource.Loading())
            val response = repository.deleteItemFromCart(cartItem.id!!)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
        }*/
        return repository.deleteItemFromCart(cartItem.id!!)
    }
}