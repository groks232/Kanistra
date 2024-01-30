package com.groks.kanistra.feature.presentation.part_details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.groks.kanistra.feature.domain.model.Part

@Composable
fun PriceRow(part: Part){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = "${part.price.toInt()} ₽",
            fontSize = 35.sp
        )
    }
}