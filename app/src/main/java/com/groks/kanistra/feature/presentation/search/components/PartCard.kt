package com.groks.kanistra.feature.presentation.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.groks.kanistra.feature.domain.model.Part

@Composable
fun PartCard(
    part: Part,
    onItemClick: () -> Unit,
    onAddToFavoritesClick: () -> Unit,
    onAddToCartClick: () -> Unit
) {
    Column(Modifier.clickable(onClick = onItemClick)) {
        Card(shape = RoundedCornerShape(8.dp),
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContainerColor = Color.Cyan,
                disabledContentColor = Color.Blue
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 180.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(size = 8.dp)
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()){
                SubcomposeAsyncImage(
                    model = if(part.images.isNotEmpty()) {
                        if(part.provider == "VAvto") "https://static.v-avto.ru${part.images[0]}"
                        else part.images[0]
                    } else "",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                ) {
                    when (painter.state) {
                        is AsyncImagePainter.State.Loading -> {
                            Box(contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }

                        is AsyncImagePainter.State.Error -> {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.Image, contentDescription = null, modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp)
                                )
                            }
                        }

                        else -> {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }

                IconButton(onClick = {
                    onAddToFavoritesClick()
                },modifier = Modifier
                    .align(alignment = Alignment.TopEnd)
                    .padding(10.dp)
                    .size(30.dp)) {
                    Icon(
                        imageVector = if(part.isInFavorites == true) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }

            }

        }

        Text(text = "${part.price.toInt() + 1} ₽",
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp)
                .align(alignment = Alignment.Start),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleLarge
        )

        Text(text = part.title,
            modifier = Modifier
                .padding(10.dp)
                .align(alignment = Alignment.Start),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(text = "${part.deliveryTime} дней",
            modifier = Modifier
                .padding(10.dp)
                .align(alignment = Alignment.Start),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Button(
            onClick = onAddToCartClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "В корзину")
        }
    }
}