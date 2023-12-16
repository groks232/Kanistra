package com.groks.kanistra.feature.domain.use_case.auth

import com.groks.kanistra.feature.domain.repository.DataStoreRepository
import javax.inject.Inject

class LogOut @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() {
        return dataStoreRepository.deleteToken()
    }
}