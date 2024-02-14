package com.groks.kanistra.feature.presentation.part_details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.groks.kanistra.feature.presentation.part_details.components.DotIndicator
import com.groks.kanistra.feature.presentation.part_details.components.PriceRow

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PartScreen(
    viewModel: PartViewModel = hiltViewModel(),
    navController: NavController
){
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "${state.part?.brand ?: ""} · ${state.part?.title ?: ""}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall
                ) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            if (state.part != null){
                val pagerState = rememberPagerState(
                    pageCount = {
                        if (state.part.images.isNotEmpty()) state.part.images.size else 2
                    }
                )

                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) {page ->
                        SubcomposeAsyncImage(
                            model =
                            if(state.part.images.isNotEmpty()) {
                                if(state.part.provider == "VAvto") "https://static.v-avto.ru${state.part.images[page]}"
                                else state.part.images[page]
                            } else  {
                                ""
                            },
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(4.dp),
                            contentScale = ContentScale.Fit
                        ) {
                            when (painter.state) {
                                is AsyncImagePainter.State.Loading -> {
                                    Box(contentAlignment = Alignment.Center) {
                                        CircularProgressIndicator()
                                    }
                                }

                                is AsyncImagePainter.State.Error -> {
                                    Icon(
                                        imageVector = Icons.Default.Image,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .height(300.dp)
                                    )
                                }

                                else -> {
                                    SubcomposeAsyncImageContent()
                                }
                            }
                        }
                    }

                    DotIndicator(pagerState = pagerState, if(state.part.images.isNotEmpty()) state.part.images.size else state.part.images.size + 2)

                    PriceRow(state.part)

                    Text(text = state.part.title,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(alignment = Alignment.Start))

                    Card(shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        border = BorderStroke(2.dp, Color.Transparent),
                    ) {
                        Text(text = "Артикул: ${state.part.partId}",
                            modifier = Modifier
                                .padding(10.dp)
                                .align(alignment = Alignment.CenterHorizontally))
                        Text(text = "Бренд запчасти: ${state.part.brand}",
                            modifier = Modifier
                                .padding(10.dp)
                                .align(alignment = Alignment.CenterHorizontally))
                        Text(text = "Количество: ${state.part.amount}",
                            modifier = Modifier
                                .padding(10.dp)
                                .align(alignment = Alignment.CenterHorizontally))
                        Text(text = "Доставка: ${state.part.deliveryTime} дней",
                            modifier = Modifier
                                .padding(10.dp)
                                .align(alignment = Alignment.CenterHorizontally))
                    }
                }
            }
            if(state.error.isNotBlank()) {
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
            if(state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }

}