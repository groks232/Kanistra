package com.groks.kanistra.feature.presentation.order.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.groks.kanistra.feature.domain.model.CartItem

@Composable
fun FAB(
    agreeToRules: Boolean,
    orderList: List<CartItem>
) {
    CompositionLocalProvider(
        LocalRippleTheme provides
            if (agreeToRules)  LocalRippleTheme.current else NoRippleTheme
    ) {

        ExtendedFloatingActionButton(
            onClick = {
                if (agreeToRules) { /* do something */ }
            },
            icon = { Icon(Icons.Filled.ShoppingCartCheckout, "Extended floating action button.") },
            text = {
                Column {
                    Text(text = "К оформлению", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "${orderList.sumOf { it.amount }} шт., ${orderList.sumOf { (it.price.toInt() + 1) * it.amount }} ₽",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            expanded = true,
            containerColor = if (agreeToRules) MaterialTheme.colorScheme.primaryContainer else Color.Gray,
            contentColor = if (agreeToRules)
                MaterialTheme.colorScheme.onPrimaryContainer
            else Color.DarkGray
        )
    }
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f, 0.0f, 0.0f, 0.0f)
}