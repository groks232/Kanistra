package com.groks.kanistra.feature.domain.use_case.favorites

import com.groks.kanistra.feature.domain.repository.KanistraRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class DeleteFavoritesItem @Inject constructor(
    private val repository: KanistraRepository
) {
    suspend operator fun invoke(id: String): ResponseBody {
        return repository.deleteFromFavorites(id)
    }
}