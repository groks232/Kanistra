package com.groks.kanistra.feature.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.groks.kanistra.feature.presentation.search.SearchEvent
import com.groks.kanistra.feature.presentation.search.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    viewModel: SearchViewModel,
    onSearch: () -> Unit
){
    var active by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    SearchBar(
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },

        trailingIcon = {
            if(active){
                IconButton(onClick = {

                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close Icon")
                }
            }

        },
        query = viewModel.searchFieldText.value.text,
        onQueryChange = {
            viewModel.onEvent(SearchEvent.EnteredPartName(it))
        },
        onSearch = {
            active = false

            onSearch()
        },
        active = active,
        onActiveChange = {
            active = it
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(10){
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                    horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = "Hint",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}