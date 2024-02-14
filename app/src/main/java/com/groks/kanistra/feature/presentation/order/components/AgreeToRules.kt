package com.groks.kanistra.feature.presentation.order.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AgreeToRules(
    modifier: Modifier,
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
fun prev() {
    val booa = remember { mutableStateOf(true)}
    AgreeToRules(
        modifier = Modifier.fillMaxWidth(),
        text = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaagree to rules agree to rules agree to rules agree to rules agree to rules agree to rules agree to rules agree to rules agree to rules agree to rules ",
        isChecked = booa.value,
        onCheckedChange = {
            booa.value = it
        }
    )
}