package com.groks.kanistra.feature.domain.use_case.cart

import com.groks.kanistra.feature.domain.repository.DataStoreRepository
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCartAmount @Inject constructor(
    private val kanistraRepository: KanistraRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Int>/*сделать flow и доделать retry по гайду индуса*/ = flow {
        emit(kanistraRepository.getCartAmount())
        /*try {

        } catch (e: HttpException){
            Log.d("Cart amount exception", e.message())
        } catch (e: IOException) {
            Log.d("Cart amount exception", "Internet connection problems")
        }*/
    }
}