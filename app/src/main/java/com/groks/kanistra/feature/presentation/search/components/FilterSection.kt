package com.groks.kanistra.feature.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.groks.kanistra.feature.domain.util.SearchFilter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(
    modifier: Modifier = Modifier,
    searchFilter: SearchFilter = SearchFilter.Price(null, null),
    onFilterChange: (SearchFilter) -> Unit,
    minimalPrice: Double,
    maximalPrice: Double
) {
    var sliderPosition by remember { mutableStateOf(minimalPrice.toInt().toFloat()..maximalPrice.toInt().toFloat() + 1) }

    Column(
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(horizontal = 30.dp)) {
            RangeSlider(
                value = sliderPosition,
                onValueChange = { range -> sliderPosition = range },
                valueRange = minimalPrice.toInt().toFloat()..maximalPrice.toInt().toFloat() + 1,
                onValueChangeFinished = {
                    onFilterChange(
                        SearchFilter.Price(sliderPosition.start.toInt(), sliderPosition.endInclusive.toInt())
                    )
                }
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "${sliderPosition.start.toInt()}")
                Text(text = "${sliderPosition.endInclusive.toInt()}")
            }
        }
    }
}