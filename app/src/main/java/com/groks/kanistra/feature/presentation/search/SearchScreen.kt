package com.groks.kanistra.feature.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.groks.kanistra.feature.presentation.search.components.OrderSection
import com.groks.kanistra.feature.presentation.search.components.SearchField
import com.groks.kanistra.feature.presentation.search.components.SearchGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)
    val isToggled = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier,
        topBar = {
            SearchField(
                viewModel = viewModel,
                onSearch = {
                    viewModel.onEvent(SearchEvent.Search)
                },
                onFilterClick = {
                    viewModel.onEvent(SearchEvent.ToggleOrderSection)
                },
                onSortClick = {
                    viewModel.onEvent(SearchEvent.ToggleOrderSection)
                }
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
                            .animateContentSize(),
                        searchOrder = state.searchOrder,
                        onOrderChange = {
                            viewModel.onEvent(SearchEvent.Order(it))
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