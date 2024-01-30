package com.groks.kanistra.feature.data.repository

import com.groks.kanistra.feature.data.remote.KanistraApi
import com.groks.kanistra.feature.data.remote.dto.LoginBody
import com.groks.kanistra.feature.data.remote.dto.RegisterBody
import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.domain.model.FavoritesItem
import com.groks.kanistra.feature.domain.model.Part
import com.groks.kanistra.feature.domain.model.SimpleResponse
import com.groks.kanistra.feature.domain.model.User
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import javax.inject.Inject

class KanistraRepositoryImpl @Inject constructor(
    private val api: KanistraApi
): KanistraRepository {
    override suspend fun register(registerBody: RegisterBody): SimpleResponse {
        return api.register(registerBody)
    }

    override suspend fun getToken(loginBody: LoginBody): SimpleResponse {
        return api.getToken(loginBody)
    }

    override suspend fun addToCart(addToCartBody: CartItem): SimpleResponse {
        return api.addToCart(addToCartBody)
    }

    override suspend fun getUserCart(): List<CartItem> {
        return api.getUserCart()
    }

    override suspend fun deleteItemFromCart(id: String): SimpleResponse {
        return api.deleteItemFromCart(id)
    }

    override suspend fun editCartItem(editCartItemBody: CartItem): SimpleResponse {
        return api.editCartItem(editCartItemBody)
    }

    override suspend fun findParts(name: String): List<Part> {
        return api.findParts(name)
    }

    override suspend fun findPart(id: String, provider: String): Part {
        return api.findPart(id, provider)
    }

    override suspend fun getUserInfo(): User {
        return api.getUserInfo()
    }

    override suspend fun editUserInfo(user: User): SimpleResponse {
        return api.editUserInfo(user)
    }

    override suspend fun deleteUser(user: User): SimpleResponse {
        return api.deleteUser(user)
    }

    override suspend fun addToFavorites(favoritesItem: FavoritesItem): SimpleResponse {
        return api.addToFavorites(favoritesItem)
    }

    override suspend fun getFavorites(): List<FavoritesItem> {
        return api.getFavorites()
    }

    override suspend fun deleteFromFavorites(id: String): SimpleResponse {
        return api.deleteFromFavorites(id)
    }
}