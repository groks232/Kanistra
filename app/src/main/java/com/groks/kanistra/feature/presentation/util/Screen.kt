package com.groks.kanistra.feature.presentation.util

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.groks.kanistra.R

sealed class Screen(
    val route: String,
    @StringRes val resourceId: Int? = null,
    var filledIcon: ImageVector? = null,
    var outlinedIcon: ImageVector? = null,
    var hasBadge: Boolean
) {
    object AuthScreen: Screen("auth_screen_route", hasBadge = false)
    object RegisterScreen: Screen("register_screen_route", hasBadge = false)
    object PartDetails: Screen("part_details_screen_route", hasBadge = false)
    object OrderScreen: Screen("order_screen_screen_route", hasBadge = false)
    object SearchScreen: Screen(
        "search_screen_route",
        R.string.search,
        Icons.Filled.Home,
        Icons.Outlined.Home,
        hasBadge = false
    )
    object CartScreen: Screen(
        "cart_screen_route",
        R.string.cart,
        Icons.Filled.ShoppingCart,
        Icons.Outlined.ShoppingCart,
        hasBadge = true
    )
    object ProfileScreen: Screen(
        "profile_screen_route",
        R.string.profile,
        Icons.Filled.AccountCircle,
        Icons.Outlined.AccountCircle,
        hasBadge = false
    )
    object FavoritesScreen: Screen(
        "favorites_screen_route",
        R.string.favorites,
        Icons.Filled.Favorite,
        Icons.Outlined.FavoriteBorder,
        hasBadge = false
    )
}
