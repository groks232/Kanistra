package com.groks.kanistra.feature.data.repository

import com.groks.kanistra.feature.data.data_source.HintDao
import com.groks.kanistra.feature.domain.model.Hint
import com.groks.kanistra.feature.domain.repository.HintRepository

class HintRepositoryImpl(
    private val dao: HintDao
): HintRepository {
    override suspend fun getHints(): List<Hint> {
        return dao.getHints()
    }

    override suspend fun putHint(hint: Hint) {
        dao.insertHint(hint)
    }

    override suspend fun deleteHint(id: Int) {
        dao.deleteHint(id)
    }
}