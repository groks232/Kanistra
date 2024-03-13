package com.groks.kanistra.feature.data.remote

import com.groks.kanistra.feature.data.remote.dto.LoginBody
import com.groks.kanistra.feature.data.remote.dto.RegisterBody
import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.domain.model.FavoritesItem
import com.groks.kanistra.feature.domain.model.Part
import com.groks.kanistra.feature.domain.model.RecentItem
import com.groks.kanistra.feature.domain.model.User
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface KanistraApi {
    //Authentication
    @POST("/register")
    suspend fun register(
        @Body registerBody: RegisterBody
    ): ResponseBody

    @POST("/login")
    suspend fun getToken(
        @Body loginBody: LoginBody
    ): ResponseBody

    //Cart
    @POST("/cart/add")
    suspend fun addToCart(
        @Body addToCartBody: CartItem
    ): ResponseBody

    @GET("/cart/getAll")
    suspend fun getUserCart(): List<CartItem>

    @GET("/cart/amount")
    suspend fun getCartAmount(): Int

    @DELETE("/cart/delete")
    suspend fun deleteItemFromCart(
        @Query("id") id: String
    ): ResponseBody

    @POST("/cart/update")
    suspend fun editCartItem(
        @Body editCartItemBody: CartItem
    ): ResponseBody

    @GET("/cart/getMultiple")
    suspend fun getMultipleCartItems(
        @Query("ids") ids: String
    ): List<CartItem>

    //Search
    @GET("/search")
    suspend fun findParts(
        @Query("name") name: String
    ): List<Part>

    @GET("/search/id")
    suspend fun findPart(
        @Query("id") id: String,
        @Query("provider") provider: String
    ): Part

    //User
    @GET("/getUser")
    suspend fun getUserInfo(): User

    @POST("/user/edit")
    suspend fun editUserInfo(
        @Body user: User
    ): ResponseBody

    @DELETE("/user/delete")
    suspend fun deleteUser(
        @Body user: User
    ): ResponseBody

    //Favorites
    @POST("/favorites/add")
    suspend fun addToFavorites(
        @Body favorite: FavoritesItem
    ): ResponseBody

    @GET("/favorites/get")
    suspend fun getFavorites(): List<FavoritesItem>

    @DELETE("/favorites/delete")
    suspend fun deleteFromFavorites(
        @Query("id") id: String
    ): ResponseBody

    //Recent
    @POST("/favorites/add")
    suspend fun addToRecent(
        @Body recentItem: RecentItem
    ): ResponseBody

    @GET("/favorites/get")
    suspend fun getRecent(): List<RecentItem>

    @DELETE("/favorites/delete")
    suspend fun deleteFromRecent(
        @Query("id") id: String
    ): ResponseBody
}