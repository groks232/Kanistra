package com.groks.kanistra.feature.domain.repository

import com.groks.kanistra.feature.data.remote.dto.FindPartsBody
import com.groks.kanistra.feature.data.remote.dto.LoginBody
import com.groks.kanistra.feature.data.remote.dto.RegisterBody
import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.domain.model.Favorite
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

    //Search
    suspend fun findParts(findPartsBody: FindPartsBody): List<Part>

    suspend fun findPart(id: String): Part

    //User
    suspend fun getUserInfo(): User

    suspend fun editUserInfo(user: User): SimpleResponse

    suspend fun deleteUser(user: User): SimpleResponse

    //Favorites
    suspend fun addToFavorites(favorite: Favorite): SimpleResponse

    suspend fun getFavorites(): List<Favorite>

    suspend fun deleteFromFavorites(favorite: Favorite): SimpleResponse
}