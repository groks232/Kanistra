package com.groks.kanistra.feature.domain.use_case.main

import com.groks.kanistra.feature.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckToken @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<Boolean> = flow {
        dataStoreRepository.getToken().collect {
            emit(!it.isNullOrBlank())
        }
    }
}