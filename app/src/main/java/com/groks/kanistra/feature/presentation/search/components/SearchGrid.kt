package com.groks.kanistra.feature.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.groks.kanistra.feature.domain.model.Part
import com.groks.kanistra.feature.presentation.util.Screen

@Composable
fun SearchGrid(
    parts: List<Part>,
    navController: NavController
){
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Adaptive(minSize = 150.dp),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
        contentPadding = PaddingValues(all = 10.dp)
    ) {
        items(parts) {
            PartCard(
                part = it,
                onItemClick = {
                    navController.navigate(Screen.PartDetails.route + "/${it.id}")
                }
            )
        }
    }
}