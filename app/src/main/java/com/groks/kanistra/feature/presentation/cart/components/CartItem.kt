package com.groks.kanistra.feature.presentation.cart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.groks.kanistra.R
import com.groks.kanistra.feature.domain.model.CartItem

@Composable
fun CartItem(
    cartItem: CartItem,
    onItemClick: (CartItem) -> Unit,
    onIncreaseClick: (CartItem) -> Unit,
    onDecreaseClick: (CartItem) -> Unit
){
    val amount = remember { mutableStateOf(cartItem.amount) }
    Row(
        modifier = Modifier
            .height(182.dp)
            .clickable { onItemClick(cartItem) }
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        SubcomposeAsyncImage(
            model = cartItem.image.ifBlank { R.drawable.placeholder },
            loading = {
                CircularProgressIndicator()
            },
            error = {
                Image(
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                )
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .width(115.dp)
                .padding(top = 4.dp, start = 4.dp, end = 4.dp, bottom = 36.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 4.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "${(cartItem.price.toInt() + 1) * amount.value} ₽",
                style = MaterialTheme.typography.titleLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = cartItem.title,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Text(
                text = "${cartItem.brand}",
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Card(
                    modifier = Modifier
                        .width(120.dp)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(onClick = {
                                amount.value -= 1
                                onDecreaseClick(CartItem(
                                    id = cartItem.id,
                                    provider = cartItem.provider,
                                    partId = cartItem.partId,
                                    image = cartItem.image,
                                    title = cartItem.title,
                                    brand = cartItem.brand,
                                    price = cartItem.price,
                                    deliveryTime = cartItem.deliveryTime,
                                    creationDate = cartItem.creationDate,
                                    amount = amount.value
                                ))
                            }) {
                                Text(
                                    text = "–",
                                    fontSize = 23.sp,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${amount.value}",
                                fontSize = 23.sp,
                                modifier = Modifier
                                    .align(Alignment.Center)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(onClick = {
                                amount.value += 1
                                onIncreaseClick(CartItem(
                                    id = cartItem.id,
                                    provider = cartItem.provider,
                                    partId = cartItem.partId,
                                    image = cartItem.image,
                                    title = cartItem.title,
                                    brand = cartItem.brand,
                                    price = cartItem.price,
                                    deliveryTime = cartItem.deliveryTime,
                                    creationDate = cartItem.creationDate,
                                    amount = amount.value
                                ))
                            }) {
                                Text(
                                    text = "+",
                                    fontSize = 23.sp,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 4.dp),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(1.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Купить",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}