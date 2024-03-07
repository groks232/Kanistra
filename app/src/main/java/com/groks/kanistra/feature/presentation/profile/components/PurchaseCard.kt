package com.groks.kanistra.feature.presentation.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PurchaseCard(
    onShippingClick: () -> Unit = {},
    onMyPurchasesClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        ProfileItem(
            mainContent = "Доставки",
            secondaryContent = "Ближайшая: ожидается/нет",
            onRowClick = onShippingClick
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            thickness = 2.dp
        )
        ProfileItem(
            mainContent = "Мои покупки",
            secondaryContent = "Оставьте отзыв",
            onRowClick = onMyPurchasesClick
        )
    }
}