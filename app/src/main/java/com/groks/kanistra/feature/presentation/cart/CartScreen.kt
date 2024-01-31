package com.groks.kanistra.feature.presentation.cart

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.presentation.cart.components.CartItem
import com.groks.kanistra.feature.presentation.util.Screen
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = isRefreshing,
            onRefresh = {
                viewModel.onEvent(CartEvent.RefreshCart)
            }
        )
    val state = viewModel.state.value

    val sum = remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier
            .pullRefresh(pullRefreshState),
        topBar = {
            TopAppBar(
                title = { Text(text = "Корзина") },
                actions = {
                    Text(text = "${state.cartList.size} элемент")
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /*TODO()*/ },
                icon = { Icon(Icons.Filled.ShoppingCartCheckout, "Extended floating action button.") },
                text = {
                    Column {
                        Text(text = "К оформлению", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "${state.cartList.size} шт., ${sum.value} ₽",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            sum.value = getSum(list = state.cartList)
            items(
                state.cartList.sortedByDescending { LocalDateTime.parse(it.creationDate)}
            ) { cartItem ->
                CartItem(
                    cartItem = cartItem,
                    onItemClick = {
                        navController.navigate(Screen.PartDetails.route + "/${it.provider}/${it.partId}")
                    },
                    onIncreaseClick = {
                        viewModel.onEvent(CartEvent.IncreaseAmount(
                            com.groks.kanistra.feature.domain.model.CartItem(
                                id = it.id,
                                provider = it.provider,
                                partId = it.partId,
                                image = it.image,
                                title = it.title,
                                brand = it.brand,
                                price = it.price,
                                deliveryTime = it.deliveryTime,
                                creationDate = it.creationDate,
                                amount = it.amount
                            )
                        ))
                        sum.value += ((it.price.toInt() + 1))
                    },
                    onDecreaseClick = {
                        viewModel.onEvent(CartEvent.DecreaseAmount(
                            com.groks.kanistra.feature.domain.model.CartItem(
                                id = it.id,
                                provider = it.provider,
                                partId = it.partId,
                                image = it.image,
                                title = it.title,
                                brand = it.brand,
                                price = it.price,
                                deliveryTime = it.deliveryTime,
                                creationDate = it.creationDate,
                                amount = it.amount
                            )
                        ))
                        sum.value -= ((it.price.toInt() + 1))
                    },
                    onRemove = {
                        viewModel.onEvent(CartEvent.DeleteCartItem(cartItem))
                    }
                )
            }
        }
        if(state.error.isNotBlank()) {
            Box(modifier = Modifier.fillMaxSize()){
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
        if(state.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        Box(modifier = Modifier.fillMaxSize()){
            PullRefreshIndicator(isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

fun getSum(list: List<CartItem>): Int{
    var sum: Int = 0
    list.forEach {
        sum += (it.price.toInt() + 1) * it.amount
    }
    return sum
}