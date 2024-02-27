package com.groks.kanistra.feature.presentation.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.groks.kanistra.common.ViewState
import com.groks.kanistra.feature.presentation.search.components.FilterSection
import com.groks.kanistra.feature.presentation.search.components.OrderSection
import com.groks.kanistra.feature.presentation.search.components.SearchField
import com.groks.kanistra.feature.presentation.search.components.SearchGrid
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToLoginScreen: () -> Unit = {},
) {
    val state = viewModel.state.value
    val viewState by viewModel.viewState.collectAsState()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showBottomSheet by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is SearchViewModel.UiEvent.ShowErrorSnackbar -> {
                    val snackbarResult = snackbarHostState.showSnackbar(
                        actionLabel = "Retry",
                        message = event.message,
                        duration = SnackbarDuration.Long
                    )
                    when(snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            viewModel.onEvent(event.searchEvent)
                        }
                        SnackbarResult.Dismissed -> {

                        }
                    }
                }
                is SearchViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short,
                        withDismissAction = true
                    )
                }
            }
        }
    }

    when(viewState){
        is ViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()){
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
        is ViewState.NotLoggedIn -> {
            LaunchedEffect(true) {
                onNavigateToLoginScreen()
            }
        }
        is ViewState.LoggedIn -> {
            if (showBottomSheet) {
                /*val windowInsets = if (edgeToEdgeEnabled)
                    WindowInsets(0) else BottomSheetDefaults.windowInsets*/
                ModalBottomSheet(
                    onDismissRequest = {
                        showBottomSheet = false
                    },
                    sheetState = sheetState
                ) {
                    Button(
                        modifier = Modifier.align(Alignment.End),
                        onClick = {
                            viewModel.onEvent(SearchEvent.ResetFilters)
                        }
                    ) {
                        Text(text = "Сбросить фильтр")
                    }
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

                    FilterSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .animateContentSize()
                        /*.background(MaterialTheme.colorScheme.secondaryContainer)*/,
                        onFilterChange = { searchFilter ->
                            viewModel.onEvent(SearchEvent.Filter(searchFilter))
                        },
                        searchFilter = state.searchFilter,
                        minimalPrice = state.partList.minOf {
                            it.price
                        },
                        maximalPrice = state.partList.maxOf {
                            it.price
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            Scaffold(
                snackbarHost = {
                    SnackbarHost(snackbarHostState)
                },
                modifier = Modifier,
                topBar = {
                    SearchField(
                        viewModel = viewModel,
                        onSearch = {
                            viewModel.onEvent(SearchEvent.Search)
                        },
                        onFilterClick = {
                            //viewModel.onEvent(SearchEvent.ToggleFilterSection)
                            showBottomSheet = true
                        },
                        state = state
                    )
                }
            ) { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    SearchGrid(
                        navController = navController,
                        parts = state.modifiedPartList,
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
    }
}