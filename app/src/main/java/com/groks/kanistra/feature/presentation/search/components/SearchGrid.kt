package com.groks.kanistra.feature.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.groks.kanistra.feature.domain.model.FavoritesItem
import com.groks.kanistra.feature.domain.model.Part
import com.groks.kanistra.feature.presentation.search.SearchEvent
import com.groks.kanistra.feature.presentation.search.SearchViewModel
import com.groks.kanistra.feature.presentation.util.Screen

@Composable
fun SearchGrid(
    paddingValues: PaddingValues,
    parts: List<Part>,
    navController: NavController,
    viewModel: SearchViewModel
){
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        columns = GridCells.Adaptive(minSize = 150.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
        contentPadding = PaddingValues(all = 10.dp)
    ) {
        items(parts) {
            PartCard(
                part = it,
                onItemClick = {
                    navController.navigate(Screen.PartDetails.route + "/${it.provider}/${it.partId}")
                },
                onAddToFavoritesClick = {
                    /*viewModel.onEvent(SearchEvent.AddToCart(
                        CartItem(
                            provider = it.provider,
                            partId = it.partId,
                            amount = 1,
                            image = if(it.images.isNotEmpty()) it.images[0] else "",
                            title = it.title,
                            brand = it.brand,
                            price = it.price,
                            deliveryTime = it.deliveryTime,
                            creationDate = ""
                        )
                    ))*/
                    viewModel.onEvent(SearchEvent.AddToFavorites(
                        FavoritesItem(
                            id = it.id.toString(),
                            partId = it.partId,
                            title = it.title,
                            brand = it.brand,
                            price = it.price,
                            deliveryTime = it.deliveryTime,
                            image = it.images.firstOrNull() ?: "",
                            provider = it.provider,
                            creationDate = ""
                        )
                    ))
                }
            )
        }
    }
}