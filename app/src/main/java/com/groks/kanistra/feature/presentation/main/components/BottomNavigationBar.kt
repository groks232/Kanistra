package com.groks.kanistra.feature.presentation.main.components

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.groks.kanistra.feature.presentation.util.Screen

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier,
    badgeCount: Int
) {
    val items = listOf(
        Screen.SearchScreen,
        Screen.CartScreen,
        Screen.FavoritesScreen,
        Screen.ProfileScreen
    )

    NavigationBar(
        modifier = modifier
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->

            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

            NavigationBarItem(
                alwaysShowLabel = false,
                label = { Text(stringResource(id = screen.resourceId!!)) },
                icon = {
                    BadgedBox(
                        badge = {
                            if (screen.hasBadge) {
                                Badge {
                                    Text(text = badgeCount.toString()) //TODO("Implement later")
                                }
                            }
                        }
                    ) {
                        Icon(
                            if (selected) screen.filledIcon!! else screen.outlinedIcon!!,
                            screen.route
                        )
                    }
                },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = false
                    }
                }
            )
        }
    }
}