package com.groks.kanistra.feature.domain.use_case.favorites

import com.groks.kanistra.feature.domain.model.Favorite
import com.groks.kanistra.feature.domain.model.SimpleResponse
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import javax.inject.Inject

class AddToFavorites @Inject constructor(
    private val repository: KanistraRepository
) {
    suspend operator fun invoke(favorite: Favorite): SimpleResponse {
        return repository.addToFavorites(favorite)
    }
}