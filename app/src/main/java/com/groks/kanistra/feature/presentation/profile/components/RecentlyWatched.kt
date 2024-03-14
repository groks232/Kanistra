package com.groks.kanistra.feature.presentation.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.groks.kanistra.feature.domain.model.RecentItem

@Composable
fun RecentlyWatched(
    list: List<RecentItem>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        ProfileItem(
            mainContent = "Вы смотрели",
            onRowClick = {

            }
        )

        LazyRow(
            modifier = Modifier
        ) {
            items(list) {
                RecentlyWatchedCard(
                    recentItem = it,
                    onItemClick = { /*TODO*/ },
                    onAddToFavoritesClick = { /*TODO*/ },
                    onAddToCartClick = { /*TODO*/ },
                    isInCart = it.isInCart ?: false,
                    modifier = Modifier
                        .width(120.dp)
                        .padding(horizontal = 10.dp)
                )
            }
        }
    }
}