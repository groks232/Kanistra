package com.groks.kanistra.feature.domain.use_case.favorites

import com.groks.kanistra.feature.domain.model.SimpleResponse
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import javax.inject.Inject

class DeleteFavoritesItem @Inject constructor(
    private val repository: KanistraRepository
) {
    suspend operator fun invoke(id: String): SimpleResponse {
        return repository.deleteFromFavorites(id)
    }
}