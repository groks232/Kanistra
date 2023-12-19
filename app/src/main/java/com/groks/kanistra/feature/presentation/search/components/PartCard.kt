package com.groks.kanistra.feature.presentation.search.components

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.groks.kanistra.R
import com.groks.kanistra.feature.domain.model.Part

@Composable
fun PartCard(
    part: Part,
    onItemClick: () -> Unit
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
                val image: Painter = painterResource(id = R.drawable.placeholder)
                if (part.images.isEmpty())
                    Image(
                        painter = image,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp))
                else
                    SubcomposeAsyncImage(
                        model = part.images[0],
                        loading = {
                            CircularProgressIndicator()
                        },
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                    )

                IconButton(onClick = {
                    /*TODO("Add to cart")*/
                },modifier = Modifier
                    .align(alignment = Alignment.TopEnd)
                    .padding(10.dp)
                    .size(30.dp)) {
                    Icon(
                        imageVector = Icons.Filled.ShoppingCart,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }

            }

        }

        Text(text = part.title,
            modifier = Modifier
                .padding(10.dp)
                .align(alignment = Alignment.Start),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}