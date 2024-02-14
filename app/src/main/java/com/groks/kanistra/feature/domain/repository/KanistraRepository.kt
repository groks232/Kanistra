package com.groks.kanistra.feature.domain.repository

import com.groks.kanistra.feature.data.remote.dto.LoginBody
import com.groks.kanistra.feature.data.remote.dto.RegisterBody
import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.domain.model.FavoritesItem
import com.groks.kanistra.feature.domain.model.Part
import com.groks.kanistra.feature.domain.model.SimpleResponse
import com.groks.kanistra.feature.domain.model.User

interface KanistraRepository {
    //Authentication
    suspend fun register(registerBody: RegisterBody): SimpleResponse

    suspend fun getToken(loginBody: LoginBody): SimpleResponse

    //Cart
    suspend fun addToCart(addToCartBody: CartItem): SimpleResponse

    suspend fun getUserCart(): List<CartItem>

    suspend fun deleteItemFromCart(id: String): SimpleResponse

    suspend fun editCartItem(editCartItemBody: CartItem): SimpleResponse

    suspend fun getMultipleCartItems(ids: String): List<CartItem>

    //Search
    suspend fun findParts(name: String): List<Part>

    suspend fun findPart(id: String, provider: String): Part

    //User
    suspend fun getUserInfo(): User

    suspend fun editUserInfo(user: User): SimpleResponse

    suspend fun deleteUser(user: User): SimpleResponse

    //Favorites
    suspend fun addToFavorites(favoritesItem: FavoritesItem): SimpleResponse

    suspend fun getFavorites(): List<FavoritesItem>

    suspend fun deleteFromFavorites(id: String): SimpleResponse
}