package com.groks.kanistra.feature.presentation.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PaymentCard(
    onPaymentMethodsClick: () -> Unit,
    onPaymentHistoryClick: () -> Unit,
    onDefectiveReturnsClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        ProfileItem(
            mainContent = "Мои способы оплаты",
            onRowClick = onPaymentMethodsClick
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            thickness = 2.dp
        )
        ProfileItem(
            mainContent = "Финансы",
            onRowClick = onPaymentHistoryClick
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            thickness = 2.dp
        )
        ProfileItem(
            mainContent = "Возвраты товара по браку",
            onRowClick = onDefectiveReturnsClick
        )
    }
}