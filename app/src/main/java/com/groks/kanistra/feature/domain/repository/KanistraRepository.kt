package com.groks.kanistra.feature.domain.repository

import com.groks.kanistra.feature.data.remote.dto.LoginBody
import com.groks.kanistra.feature.data.remote.dto.RegisterBody
import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.domain.model.FavoritesItem
import com.groks.kanistra.feature.domain.model.Part
import com.groks.kanistra.feature.domain.model.User
import okhttp3.ResponseBody

interface KanistraRepository {
    //Authentication
    suspend fun register(registerBody: RegisterBody): ResponseBody

    suspend fun getToken(loginBody: LoginBody): ResponseBody

    //Cart
    suspend fun addToCart(addToCartBody: CartItem): ResponseBody

    suspend fun getUserCart(): List<CartItem>

    suspend fun deleteItemFromCart(id: String): ResponseBody

    suspend fun editCartItem(editCartItemBody: CartItem): ResponseBody

    suspend fun getMultipleCartItems(ids: String): List<CartItem>

    suspend fun getCartAmount(): Int

    //Search
    suspend fun findParts(name: String): List<Part>

    suspend fun findPart(id: String, provider: String): Part

    //User
    suspend fun getUserInfo(): User

    suspend fun editUserInfo(user: User): ResponseBody

    suspend fun deleteUser(user: User): ResponseBody

    //Favorites
    suspend fun addToFavorites(favoritesItem: FavoritesItem): ResponseBody

    suspend fun getFavorites(): List<FavoritesItem>

    suspend fun deleteFromFavorites(id: String): ResponseBody
}