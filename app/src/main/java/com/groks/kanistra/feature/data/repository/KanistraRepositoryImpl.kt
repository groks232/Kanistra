package com.groks.kanistra.feature.data.repository

import com.groks.kanistra.feature.data.remote.KanistraApi
import com.groks.kanistra.feature.data.remote.dto.LoginBody
import com.groks.kanistra.feature.data.remote.dto.RegisterBody
import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.domain.model.FavoritesItem
import com.groks.kanistra.feature.domain.model.Part
import com.groks.kanistra.feature.domain.model.RecentItem
import com.groks.kanistra.feature.domain.model.User
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class KanistraRepositoryImpl @Inject constructor(
    private val api: KanistraApi
): KanistraRepository {
    override suspend fun register(registerBody: RegisterBody): ResponseBody {
        return api.register(registerBody)
    }

    override suspend fun getToken(loginBody: LoginBody): ResponseBody {
        return api.getToken(loginBody)
    }

    override suspend fun addToCart(addToCartBody: CartItem): ResponseBody {
        return api.addToCart(addToCartBody)
    }

    override suspend fun getCartAmount(): Int {
        return api.getCartAmount()
    }

    override suspend fun getUserCart(): List<CartItem> {
        return api.getUserCart()
    }

    override suspend fun deleteItemFromCart(id: String): ResponseBody {
        return api.deleteItemFromCart(id)
    }

    override suspend fun editCartItem(editCartItemBody: CartItem): ResponseBody {
        return api.editCartItem(editCartItemBody)
    }

    override suspend fun getMultipleCartItems(ids: String): List<CartItem> {
        return api.getMultipleCartItems(ids)
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

    override suspend fun editUserInfo(user: User): ResponseBody {
        return api.editUserInfo(user)
    }

    override suspend fun deleteUser(user: User): ResponseBody {
        return api.deleteUser(user)
    }

    override suspend fun addToFavorites(favoritesItem: FavoritesItem): ResponseBody {
        return api.addToFavorites(favoritesItem)
    }

    override suspend fun getFavorites(): List<FavoritesItem> {
        return api.getFavorites()
    }

    override suspend fun deleteFromFavorites(id: String): ResponseBody {
        return api.deleteFromFavorites(id)
    }

    override suspend fun addToRecent(recentItem: RecentItem): ResponseBody {
        return api.addToRecent(recentItem)
    }

    override suspend fun getRecent(): List<RecentItem> {
        return api.getRecent()
    }

    override suspend fun deleteFromRecent(id: String): ResponseBody {
        return api.deleteFromRecent(id)
    }
}