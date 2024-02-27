package com.groks.kanistra.feature.domain.use_case.cart

import com.groks.kanistra.feature.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReadCartAmount @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Int> = flow {
        dataStoreRepository.getCartCount().collect {
            emit(it)
        }
    }
}