package com.groks.kanistra.feature.presentation.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.groks.kanistra.feature.domain.util.OrderType
import com.groks.kanistra.feature.domain.util.SearchOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    searchOrder: SearchOrder = SearchOrder.Price(OrderType.Descending),
    onOrderChange: (SearchOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            DefaultRadioButton(
                text = "Price",
                selected = searchOrder is SearchOrder.Price,
                onSelect = { onOrderChange(SearchOrder.Price(searchOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = searchOrder is SearchOrder.DeliveryDate,
                onSelect = { onOrderChange(SearchOrder.DeliveryDate(searchOrder.orderType)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = searchOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(searchOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = searchOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(searchOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}