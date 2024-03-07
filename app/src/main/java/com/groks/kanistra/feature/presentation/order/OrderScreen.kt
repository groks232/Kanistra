package com.groks.kanistra.feature.presentation.order

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.groks.kanistra.feature.presentation.order.components.AgreeToRules
import com.groks.kanistra.feature.presentation.order.components.DateCard
import com.groks.kanistra.feature.presentation.order.components.DeliveryPoint
import com.groks.kanistra.feature.presentation.order.components.FAB
import com.groks.kanistra.feature.presentation.order.components.TotalPricing
import com.groks.kanistra.feature.presentation.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    viewModel: OrderViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Оформление заказа") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FAB(
                agreeToRules = state.agreeToRules,
                orderList = state.orderList,
                onNavigateToPayment = {
                    navController.navigate(Screen.PaymentScreen.route)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())

        ) {
            DeliveryPoint()
            Spacer(modifier = Modifier.height(10.dp))
            DateCard(orderList = state.orderList)
            TotalPricing(
                orderCount = state.orderList.sumOf { cartItem ->
                    cartItem.amount
                },
                orderPrice = state.orderList.sumOf {cartItem ->
                    cartItem.price.toInt() * cartItem.amount
                },
                paddingValues = PaddingValues(10.dp)
            )
            AgreeToRules(
                modifier = Modifier,
                text = "Согласен с условиями Правил пользования торговой площадкой и правилами возврата",
                isChecked = state.agreeToRules,
                onCheckedChange = {
                    viewModel.onEvent(OrderEvent.AgreeToRules)
                }
            )
            Spacer(modifier = Modifier.height(70.dp))
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
    }
}