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
    var outlinedIcon: ImageVector? = null
) {
    object AuthScreen: Screen("auth_screen_route")
    object PartDetails: Screen("part_details_screen_route")
    object SearchScreen: Screen(
        "search_screen_route",
        R.string.search,
        Icons.Filled.Home,
        Icons.Outlined.Home
    )
    object CartScreen: Screen(
        "cart_screen_route",
        R.string.cart,
        Icons.Filled.ShoppingCart,
        Icons.Outlined.ShoppingCart
    )
    object ProfileScreen: Screen(
        "profile_screen_route",
        R.string.profile,
        Icons.Filled.AccountCircle,
        Icons.Outlined.AccountCircle
    )
    object FavoritesScreen: Screen(
        "favorites_screen_route",
        R.string.favorites,
        Icons.Filled.Favorite,
        Icons.Outlined.FavoriteBorder
    )
}
