package com.groks.kanistra.feature.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getToken(): Flow<String?>

    suspend fun saveToken(token: String)

    suspend fun deleteToken()
}