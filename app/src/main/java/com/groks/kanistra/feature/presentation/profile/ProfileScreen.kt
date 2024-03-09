package com.groks.kanistra.feature.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.groks.kanistra.feature.presentation.profile.components.PaymentCard
import com.groks.kanistra.feature.presentation.profile.components.ProfileItem
import com.groks.kanistra.feature.presentation.profile.components.ProfileRow
import com.groks.kanistra.feature.presentation.profile.components.PurchaseCard
import com.groks.kanistra.feature.presentation.profile.components.RecentlyWatched
import com.groks.kanistra.feature.presentation.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.value
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (showBottomSheet) {
        /*val windowInsets = if (edgeToEdgeEnabled)
            WindowInsets(0) else BottomSheetDefaults.windowInsets*/
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                thickness = 1.dp
            )

            Row(
                modifier = Modifier
                    .padding(start = 10.dp)
            ) {
                Icon(imageVector = Icons.Default.Payment, contentDescription = null, modifier = Modifier.align(
                    Alignment.CenterVertically))

                ProfileItem(
                    mainContent = "SberPay **7393",
                    secondaryContent = "Основная карта",
                    icon = Icons.Default.Close
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                thickness = 1.dp
            )
            Row(
                modifier = Modifier
                    .padding(start = 10.dp)
            ) {
                Icon(imageVector = Icons.Default.AddCard, contentDescription = null, modifier = Modifier.align(
                    Alignment.CenterVertically))

                ProfileItem(
                    mainContent = "Добавить карту"
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

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
                        ProfileRow(state.user.fullName) {
                            navController.navigate(Screen.ProfileData.route)
                        }
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
                                showBottomSheet = true
                            },
                            onPaymentHistoryClick = {
                                navController.navigate(Screen.PaymentHistory.route)
                            },
                            onDefectiveReturnsClick = {
                                navController.navigate(Screen.Returns.route)
                            }
                        )
                        RecentlyWatched(state.favoritesList)
                    }
                }
                if (state.error.isNotBlank()) {
                    Text(text = state.error)
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