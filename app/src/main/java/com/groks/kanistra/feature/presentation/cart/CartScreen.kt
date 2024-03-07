package com.groks.kanistra.feature.presentation.cart

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.groks.kanistra.feature.domain.model.CartItem
import com.groks.kanistra.feature.presentation.cart.components.CartItem
import com.groks.kanistra.feature.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
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

    val snackbarHostState = remember { SnackbarHostState() }

    val haptics = LocalHapticFeedback.current

    val list: MutableList<MutableState<Boolean>> = remember { mutableListOf() }

    val totalAmount = remember { mutableStateOf(0)}

    val sum = remember { mutableStateOf(0) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is CartViewModel.UiEvent.ShowErrorSnackbar -> {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        actionLabel = "Retry",
                        message = event.message,
                        duration = SnackbarDuration.Long
                    )
                    when(snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.onEvent(event.cartEvent)
                        }
                        SnackbarResult.Dismissed -> {

                        }
                    }
                }
                is CartViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .pullRefresh(pullRefreshState),
        topBar = {
            TopAppBar(
                title = { Text(text = "Корзина") },
                actions = {
                    Text(text = "${state.cartList.sumOf { it.amount }} элемент")
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val newList = mutableListOf<CartItem>()
                    for(i in 0 until state.cartList.sortedByDescending { LocalDateTime.parse(it.creationDate) }.size){
                        if(list[i].value) newList.add(state.cartList.sortedByDescending { LocalDateTime.parse(it.creationDate) }[i])
                    }

                    val orderString = if(newList.isEmpty()) state.cartList.joinToString(separator = ",") {
                        "${it.id}"
                    }
                    else {
                        newList.joinToString(separator = ",") {
                            "${it.id}"
                        }
                    }

                    navController.navigate(Screen.OrderScreen.route + "/${orderString}")
                },
                icon = { Icon(Icons.Filled.ShoppingCartCheckout, "Extended floating action button.") },
                text = {
                    Column {
                        Text(text = "К оформлению", style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = "${/*state.cartList.sumOf { it.amount }*/totalAmount.value} шт., ${sum.value} ₽",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                },
                expanded = true
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->
        list.clear()
        state.cartList.forEach { _ ->
            list.add(mutableStateOf(false))
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val newList = mutableListOf<CartItem>()
            for(i in 0 until state.cartList.sortedByDescending { LocalDateTime.parse(it.creationDate) }.size){
                if(list[i].value) newList.add(state.cartList.sortedByDescending { LocalDateTime.parse(it.creationDate) }[i])
            }

            if(newList.isEmpty()) {
                sum.value = getSum(list = state.cartList)
                totalAmount.value = getAmount(list = state.cartList)
            }
            else {
                sum.value = getSum(newList)
                totalAmount.value = getAmount(list = newList)
            }
            itemsIndexed(
                state.cartList.sortedByDescending { LocalDateTime.parse(it.creationDate) }
            ) { index, cartItem ->
                var onPressedIndex by remember { mutableStateOf(-1) }
                if(onPressedIndex != -1) {
                    if(state.isSelectionEnabled) list[onPressedIndex].value = true
                    onPressedIndex = -1
                }
                BackHandler(enabled = state.isSelectionEnabled) {
                    viewModel.onEvent(CartEvent.EnableSelection)
                }

                CartItem(
                    isSelectionEnabled = state.isSelectionEnabled,
                    modifier = Modifier
                        .combinedClickable(
                            onClick = {
                                if(!state.isSelectionEnabled)
                                    navController.navigate(Screen.PartDetails.route + "/${cartItem.provider}/${cartItem.partId}")
                                else
                                    list[index].value = !list[index].value
                            },
                            onLongClick = {
                                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.onEvent(CartEvent.EnableSelection)
                                onPressedIndex = index
                            }
                        ),
                    isChecked = list[index].value,
                    onCheckedChange = {
                        list[index].value = it
                    },
                    cartItem = cartItem,
                    onIncreaseClick = {
                        viewModel.onEvent(CartEvent.IncreaseAmount(
                            CartItem(
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
                    },
                    onOrderClick = {
                        val orderString = cartItem.id
                        navController.navigate(Screen.OrderScreen.route + "/${orderString}")
                    }
                )
            }
            item {
                Spacer(modifier = Modifier.height(70.dp))
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

fun getAmount(list: List<CartItem>): Int {
    var amount = 0
    list.forEach {
        amount += it.amount
    }
    return amount
}