package com.groks.kanistra.feature.domain.use_case.cart

import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.domain.repository.DataStoreRepository
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddToCart @Inject constructor(
    private val repository: KanistraRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(cartItem: CartItem): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.addToCart(cartItem)
            dataStoreRepository.getCartCount()
            emit(Resource.Success(result.string()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.response()?.errorBody()?.string() ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
        }
    }
}