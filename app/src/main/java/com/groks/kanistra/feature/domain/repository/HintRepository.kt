package com.groks.kanistra.feature.domain.repository

import com.groks.kanistra.feature.domain.model.Hint

interface HintRepository {
    suspend fun getHints(): Hint?

    suspend fun putHint(hint: Hint)

    suspend fun deleteHint()
}