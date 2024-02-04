package com.groks.kanistra.feature.presentation.search.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.groks.kanistra.feature.domain.util.OrderType
import com.groks.kanistra.feature.domain.util.SearchFilter
import com.groks.kanistra.feature.domain.util.SearchOrder

@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    searchFilter: SearchFilter = SearchFilter.Price(SearchOrder.Price(OrderType.Descending)),
    onFilterChange: (SearchFilter, Int, Int) -> Unit,
    minimalPrice: Double,
    maximalPrice: Double
) {
    val minPrice = remember{mutableStateOf("")}
    val maxPrice = remember{mutableStateOf("")}

    val pattern = remember { Regex("^\\d+\$") }

    Column(
        modifier = modifier
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = minPrice.value,
                    onValueChange = { newText ->
                        if (newText.isEmpty() || newText.matches(pattern))
                            minPrice.value = newText
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(0.5f),
                    placeholder = {
                        Text(text = "От: ${minimalPrice.toInt()}")
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                TextField(
                    value = maxPrice.value,
                    onValueChange = { newText ->
                        if (newText.isEmpty() || newText.matches(pattern))
                            maxPrice.value = newText
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(0.5f),
                    placeholder = {
                        Text(text = "До: ${maximalPrice.toInt() + 1}")
                    }
                )
            }

            Button(
                onClick = { onFilterChange(searchFilter, minPrice.value.toInt(), maxPrice.value.toInt()) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(150.dp)
            ) {
                Text(text = "Filter")
            }
        }
    }
}