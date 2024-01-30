package com.groks.kanistra.feature.presentation.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.use_case.favorites.FavoritesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesUseCases: FavoritesUseCases
): ViewModel() {
    private val _state = mutableStateOf(FavoritesState())
    val state: State<FavoritesState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        onEvent(FavoritesEvent.GetFavorites)
    }

    fun onEvent(event: FavoritesEvent) {
        when(event) {
            is FavoritesEvent.GetFavorites -> {
                favoritesUseCases.getFavorites().onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = FavoritesState(favoritesList = result.data ?: emptyList())
                        }

                        is Resource.Error -> {
                            _state.value =
                                FavoritesState(error = result.message ?: "An unexpected error occurred.")
                        }

                        is Resource.Loading -> {
                            _state.value = FavoritesState(isLoading = true)
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is FavoritesEvent.RefreshFavorites -> {
                viewModelScope.launch {
                    _isRefreshing.value = true
                    onEvent(FavoritesEvent.GetFavorites)
                    delay(1000L)
                    _isRefreshing.value = false
                }
            }
            is FavoritesEvent.DeleteFavoritesItem -> {
                viewModelScope.launch {
                    favoritesUseCases.deleteFavoritesItem(
                        event.favoritesItem.id
                    )
                }
            }
            is FavoritesEvent.RestoreFavoritesItem -> {

            }
        }
    }
}