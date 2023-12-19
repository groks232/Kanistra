package com.groks.kanistra.feature.data.remote

import com.groks.kanistra.feature.data.remote.dto.LoginBody
import com.groks.kanistra.feature.data.remote.dto.RegisterBody
import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.domain.model.Favorite
import com.groks.kanistra.feature.domain.model.Part
import com.groks.kanistra.feature.domain.model.SimpleResponse
import com.groks.kanistra.feature.domain.model.User
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
    ): SimpleResponse

    @POST("/login")
    suspend fun getToken(
        @Body loginBody: LoginBody
    ): SimpleResponse

    //Cart
    @POST("/cart/add")
    suspend fun addToCart(
        @Body addToCartBody: CartItem
    ): SimpleResponse

    @GET("/cart/getAll")
    suspend fun getUserCart(): List<CartItem>

    @DELETE("/cart/delete")
    suspend fun deleteItemFromCart(
        @Query("id") id: String
    ): SimpleResponse

    @POST("/cart/edit")
    suspend fun editCartItem(
        @Body editCartItemBody: CartItem
    ): SimpleResponse

    //Search
    @GET("/search")
    suspend fun findParts(
        @Query("name") name: String
    ): List<Part>

    @GET("/search/id")
    suspend fun findPart(
        @Query("id") id: String
    ): Part

    //User
    @GET("/getUser")
    suspend fun getUserInfo(): User

    @POST("/user/edit")
    suspend fun editUserInfo(
        @Body user: User
    ): SimpleResponse

    @DELETE("/user/delete")
    suspend fun deleteUser(
        @Body user: User
    ): SimpleResponse

    //Favorites
    @POST("favorites/add")
    suspend fun addToFavorites(
        @Body favorite: Favorite
    ): SimpleResponse

    @GET("/favorites/get")
    suspend fun getFavorites(): List<Favorite>

    @DELETE("/favorites/delete")
    suspend fun deleteFromFavorites(
        @Body favorite: Favorite
    ): SimpleResponse
}