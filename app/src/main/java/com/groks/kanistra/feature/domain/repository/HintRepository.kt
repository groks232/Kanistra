package com.groks.kanistra.feature.domain.repository

import com.groks.kanistra.feature.domain.model.Hint

interface HintRepository {
    suspend fun getHints(): List<Hint>

    suspend fun putHint(hint: Hint)

    suspend fun deleteHint(id: Int)
}