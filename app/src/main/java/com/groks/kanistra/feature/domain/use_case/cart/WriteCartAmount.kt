package com.groks.kanistra.feature.domain.use_case.cart

import com.groks.kanistra.feature.domain.repository.DataStoreRepository
import javax.inject.Inject

class WriteCartAmount @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(cartAmount: Int) {
        dataStoreRepository.saveCartCount(cartAmount)
    }
}