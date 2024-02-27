package com.groks.kanistra.feature.domain.use_case.favorites

import com.groks.kanistra.feature.domain.model.FavoritesItem
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class AddToFavorites @Inject constructor(
    private val repository: KanistraRepository
) {
    suspend operator fun invoke(favoritesItem: FavoritesItem): ResponseBody {
        return repository.addToFavorites(favoritesItem)
    }
}