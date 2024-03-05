package com.groks.kanistra.feature.presentation.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.use_case.cart.CartUseCases
import com.groks.kanistra.feature.domain.use_case.favorites.FavoritesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesUseCases: FavoritesUseCases,
    private val cartUseCases: CartUseCases
): ViewModel() {
    private val _state = mutableStateOf(FavoritesState())
    val state: State<FavoritesState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                    _isRefreshing.emit(true)
                    onEvent(FavoritesEvent.GetFavorites)
                    delay(3000L)
                    _isRefreshing.emit(false)
                }
            }
            is FavoritesEvent.DeleteFavoritesItem -> {
                favoritesUseCases.deleteFavoritesItem(
                    event.favoritesItem.id
                ).onEach { result ->
                    when(result){
                        is Resource.Success -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = result.data ?: "Something happened."
                                )
                            )
                            _state.value = _state.value.copy(favoritesList = _state.value.favoritesList.filter {
                                it.id != event.favoritesItem.id
                            }.toMutableList())
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowErrorSnackbar(
                                    message = result.message ?: "Unexpected error occurred.",
                                    favoritesEvent = event
                                )
                            )
                        }
                        is Resource.Loading -> {

                        }
                    }
                }.launchIn(viewModelScope)
            }
            is FavoritesEvent.RestoreFavoritesItem -> {

            }
            is FavoritesEvent.AddToCart -> {
                cartUseCases.addToCart(event.cartItem).onEach {  result ->
                    when(result) {
                        is Resource.Success -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(
                                    message = result.data ?: "Something happened."
                                )
                            )
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowErrorSnackbar(
                                    message = result.message ?: "Unexpected error occurred.",
                                    favoritesEvent = event
                                )
                            )
                        }
                        is Resource.Loading -> {

                        }
                    }

                }.launchIn(viewModelScope)
            }
        }
    }
    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        data class ShowErrorSnackbar(val message: String, val favoritesEvent: FavoritesEvent): UiEvent()
    }
}