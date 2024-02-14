package com.groks.kanistra.feature.presentation.order.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.groks.kanistra.feature.domain.model.CartItem

@Composable
fun DateCard(
   orderList: List<CartItem>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            //.height(200.dp)
        ,
        shape = RoundedCornerShape(25.dp),
    ) {
        val grouped = orderList.groupBy {
            it.deliveryTime
        }
        grouped.keys.sortedBy { grouped[it]?.get(0)?.deliveryTime }.forEach {
            grouped.get(it)?.let { orderSublist ->
                //DateCard(orderList = it1)
                Column {
                    Text(
                        text = "Доставим через ${orderSublist.get(0).deliveryTime} дней",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .fillMaxWidth()
                            .height(210.dp)
                    ) {
                        orderSublist.forEach { orderItem ->
                            Column(
                                modifier = Modifier
                                    .width(120.dp)
                                    .padding(
                                        top = 16.dp,
                                        start = 16.dp,
                                        end = 4.dp,
                                    )
                            ) {
                                SubcomposeAsyncImage(
                                    model = orderItem.image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.White),
                                    contentScale = ContentScale.Crop
                                ) {
                                    when (painter.state) {
                                        is AsyncImagePainter.State.Loading -> {
                                            Box(contentAlignment = Alignment.Center) {
                                                CircularProgressIndicator()
                                            }
                                        }

                                        is AsyncImagePainter.State.Error -> {
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier
                                                    .width(100.dp)
                                                    .height(150.dp)
                                            ) {
                                                Image(
                                                    imageVector = Icons.Default.Image,
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                )
                                            }
                                        }

                                        else -> {
                                            SubcomposeAsyncImageContent()
                                        }
                                    }
                                }
                                Text(
                                    text = orderItem.title,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 2,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = 4.dp),
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, start = 12.dp, end = 12.dp),
                        thickness = 2.dp
                    )
                }
            }
        }


    }
}

@Preview
@Composable
fun DateCardPreview() {
    val list = listOf<CartItem>(
        CartItem(
            id = null,
            provider = "rossko",
            partId = "fsf",
            amount = 1,
            image = "",
            title = "fdfgsgf",
            brand = "",
            price = 100.0,
            deliveryTime = 1,
            creationDate = null
        ),
        CartItem(
            id = null,
            provider = "vavto",
            partId = "adfsf",
            amount = 1,
            image = "",
            title = "fdsdfg",
            brand = "",
            price = 100.0,
            deliveryTime = 1,
            creationDate = null
        )
    )
    Column {
        DeliveryPoint()

        Spacer(modifier = Modifier.height(10.dp))

        DateCard(orderList = list)
    }
}