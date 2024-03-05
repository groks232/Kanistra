package com.groks.kanistra.feature.presentation.favorites

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.presentation.favorites.components.FavoritesItem
import com.groks.kanistra.feature.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun FavoritesScreen(
    navController: NavController, viewModel: FavoritesViewModel = hiltViewModel()
) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = {
        viewModel.onEvent(FavoritesEvent.RefreshFavorites)
    })
    val state = viewModel.state.value

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is FavoritesViewModel.UiEvent.ShowErrorSnackbar -> {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        actionLabel = "Retry",
                        message = event.message,
                        duration = SnackbarDuration.Long
                    )
                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.onEvent(event.favoritesEvent)
                        }

                        SnackbarResult.Dismissed -> {

                        }
                    }
                }

                is FavoritesViewModel.UiEvent.ShowSnackbar -> {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        actionLabel = "Восстановить",
                        message = event.message,
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.onEvent(FavoritesEvent.RestoreFavoritesItem)
                        }

                        SnackbarResult.Dismissed -> {

                        }
                    }
                }
            }
        }
    }

    Scaffold(modifier = Modifier.pullRefresh(pullRefreshState), topBar = {
        TopAppBar(title = { Text(text = "Избранное") }, actions = {
            Text(text = "${state.favoritesList.size} элемент")
        })
    }, snackbarHost = {
        SnackbarHost(snackbarHostState)
    }) { paddingValues ->

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            columns = GridCells.Adaptive(minSize = 150.dp),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
            contentPadding = PaddingValues(all = 10.dp)
        ) {
            itemsIndexed(state.favoritesList) { index, favoritesItem ->
                FavoritesItem(favoritesItem = favoritesItem, onItemClick = {
                    navController.navigate(Screen.PartDetails.route + "/${favoritesItem.provider}/${favoritesItem.partId}")
                }, onAddToFavoritesClick = {
                    viewModel.onEvent(FavoritesEvent.DeleteFavoritesItem(favoritesItem))
                }, onAddToCartClick = {
                    viewModel.onEvent(
                        FavoritesEvent.AddToCart(
                            CartItem(
                                provider = favoritesItem.provider,
                                partId = favoritesItem.partId,
                                amount = 1,
                                image = favoritesItem.image ?: "",
                                title = favoritesItem.title,
                                brand = favoritesItem.brand,
                                price = favoritesItem.price,
                                deliveryTime = favoritesItem.deliveryTime,
                                creationDate = "null"
                            )
                        )
                    )
                }, isInCart = favoritesItem.isInCart ?: false
                )
            }
        }

        if (state.error.isNotBlank()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            PullRefreshIndicator(
                isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter)
            )
        }
    }
}