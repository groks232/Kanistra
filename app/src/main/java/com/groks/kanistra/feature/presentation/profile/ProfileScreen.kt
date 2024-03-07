package com.groks.kanistra.feature.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.groks.kanistra.common.ViewState
import com.groks.kanistra.feature.presentation.profile.components.PaymentCard
import com.groks.kanistra.feature.presentation.profile.components.ProfileRow
import com.groks.kanistra.feature.presentation.profile.components.PurchaseCard
import com.groks.kanistra.feature.presentation.profile.components.RecentlyWatched
import com.groks.kanistra.feature.presentation.util.Screen

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController,
    onNavigateToLoginScreen: () -> Unit = {}
) {
    val viewState by viewModel.viewState.collectAsState()

    when(viewState){
        is ViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        is ViewState.NotLoggedIn -> {
            LaunchedEffect(true) {
                onNavigateToLoginScreen()
            }
        }
        is ViewState.LoggedIn -> {
            val state = viewModel.state.value
            Scaffold { paddingValues ->
                when(state.isLoading) {
                    true -> {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                    false -> {
                        if (state.user != null) {
                            Column(
                                modifier = Modifier
                                    .padding(paddingValues)
                                    .padding(horizontal = 10.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                ProfileRow(state.user.fullName)
                                PurchaseCard(
                                    onShippingClick = {
                                        navController.navigate(Screen.Shippings.route)
                                    },
                                    onMyPurchasesClick = {
                                        navController.navigate(Screen.Purchases.route)
                                    }
                                )
                                PaymentCard(
                                    onPaymentMethodsClick = {

                                    },
                                    onPaymentHistoryClick = {
                                        navController.navigate(Screen.PaymentHistory.route)
                                    },
                                    onDefectiveReturnsClick = {
                                        navController.navigate(Screen.Returns.route)
                                    }
                                )
                                RecentlyWatched(state.favoritesList)
                                OutlinedButton(
                                    onClick = {
                                        viewModel.logOut()
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(text = "Log out")
                                }
                            }
                        }
                        if (state.error.isNotBlank()) {
                            Text(text = state.error)
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}