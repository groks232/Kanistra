package com.groks.kanistra.feature.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.groks.kanistra.common.Constants.dataStore
import com.groks.kanistra.feature.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepositoryImpl(
    private val context: Context
): DataStoreRepository {
    companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val CART_COUNT = intPreferencesKey("cart_count")
    }

    override fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    override suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    override fun getCartCount(): Flow<Int> {
        return context.dataStore.data.map {  preferences ->
            preferences[CART_COUNT] ?: 0
        }
    }

    override suspend fun saveCartCount(cartCount: Int) {
        context.dataStore.edit {  preferences ->
            preferences[CART_COUNT] = cartCount
        }
    }
}