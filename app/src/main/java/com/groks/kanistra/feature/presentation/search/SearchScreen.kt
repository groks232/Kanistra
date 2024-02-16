package com.groks.kanistra.feature.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.groks.kanistra.feature.presentation.search.components.FilterSection
import com.groks.kanistra.feature.presentation.search.components.OrderSection
import com.groks.kanistra.feature.presentation.search.components.SearchField
import com.groks.kanistra.feature.presentation.search.components.SearchGrid

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Scaffold(
        modifier = Modifier,
        topBar = {
            SearchField(
                viewModel = viewModel,
                onSearch = {
                    viewModel.onEvent(SearchEvent.Search)
                },
                onFilterClick = {
                    viewModel.onEvent(SearchEvent.ToggleFilterSection)
                },
                onSortClick = {
                    viewModel.onEvent(SearchEvent.ToggleOrderSection)
                },
                state = state
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    /*.animateContentSize()
                    .height(if (isToggled.value) 300.dp else 0.dp)*/
            ) {
                AnimatedVisibility(visible = state.isOrderSectionVisible) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .animateContentSize()
                            /*.background(MaterialTheme.colorScheme.secondaryContainer)*/,
                        searchOrder = state.searchOrder,
                        onOrderChange = {
                            viewModel.onEvent(SearchEvent.Order(it))
                        }
                    )
                }
                AnimatedVisibility(visible = state.isFilterSectionVisible) {
                    FilterSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .animateContentSize()
                            /*.background(MaterialTheme.colorScheme.secondaryContainer)*/,
                        onFilterChange = { searchFilter, minPrice, maxPrice ->
                            viewModel.onEvent(SearchEvent.Filter(searchFilter, minPrice, maxPrice))
                        },
                        searchFilter = state.searchFilter,
                        minimalPrice = state.partList.minOf {
                            it.price
                        },
                        maximalPrice = state.partList.maxOf {
                            it.price
                        }
                    )
                }
            }

            SearchGrid(
                navController = navController,
                parts = state.partList,
                paddingValues = paddingValues,
                viewModel = viewModel
            )
        }
        if(state.error.isNotBlank()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }
        if(state.isLoading) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}