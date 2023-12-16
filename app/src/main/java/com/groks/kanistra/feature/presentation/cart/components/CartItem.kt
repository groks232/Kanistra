package com.groks.kanistra.feature.presentation.cart.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.groks.kanistra.feature.domain.model.CartItem

@Composable
fun CartItem(
    cartItem: CartItem,
    onItemClick: (CartItem) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(cartItem) }
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${cartItem.individualPartNumber}, ${cartItem.providerType}",
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${cartItem.id}",
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis
        )
    }
}