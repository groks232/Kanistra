package com.groks.kanistra.feature.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.use_case.cart.CartUseCases
import com.groks.kanistra.feature.domain.use_case.favorites.FavoritesUseCases
import com.groks.kanistra.feature.domain.use_case.hint.HintUseCases
import com.groks.kanistra.feature.domain.use_case.parts.FindParts
import com.groks.kanistra.feature.domain.util.OrderType
import com.groks.kanistra.feature.domain.util.SearchFilter
import com.groks.kanistra.feature.domain.util.SearchOrder
import com.groks.kanistra.feature.presentation.auth.AuthTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val findParts: FindParts,
    private val cartUseCases: CartUseCases,
    private val hintUseCases: HintUseCases,
    private val favoritesUseCases: FavoritesUseCases
): ViewModel(){
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private val _searchFieldText = mutableStateOf(
        AuthTextFieldState(hint = "Enter password")
    )
    val searchFieldText: State<AuthTextFieldState> = _searchFieldText

    private val _hintState = mutableStateOf(HintState())
    val hintState: State<HintState> = _hintState


    fun onEvent(event: SearchEvent) {
        when(event){
            is SearchEvent.EnteredPartName -> {
                _searchFieldText.value = searchFieldText.value.copy(
                    text = event.value
                )
            }
            is SearchEvent.AddToCart -> {
                viewModelScope.launch {
                    cartUseCases.addToCart(
                        cartItem = event.cartItem
                    )
                }
            }
            is SearchEvent.AddToFavorites -> {
                viewModelScope.launch {
                    favoritesUseCases.addToFavorites(
                        favoritesItem = event.favoritesItem
                    )
                }
            }
            is SearchEvent.Search -> {
                findParts(searchFieldText.value.text).onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = SearchState(
                                partList = result.data ?: emptyList()
                            )
                        }

                        is Resource.Error -> {
                            _state.value = SearchState(
                                error = result.message ?: "unexpected error occurred"
                            )
                            /*_eventFlow.emit(
                                AuthViewModel.UiEvent.ShowSnackbar(
                                    message = result.message ?: "Unexpected error occurred."
                                )
                            )*/
                        }

                        is Resource.Loading -> {
                            _state.value = SearchState(isLoading = true)
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is SearchEvent.GetHints -> {
                viewModelScope.launch {
                    _hintState.value = _hintState.value.copy(
                        hintList = hintUseCases.getHints()
                    )
                }
            }
            is SearchEvent.AddHint -> {
                viewModelScope.launch {
                    hintUseCases.insertHint(
                        hint = event.hint
                    )
                }
            }
            is SearchEvent.DeleteHint -> {
                viewModelScope.launch {
                    hintUseCases.deleteHint(event.hint.id)
                }
            }
            is SearchEvent.Order -> {
                if (state.value.searchOrder.orderType == event.searchOrder.orderType &&
                    state.value.searchOrder::class == event.searchOrder::class
                ) {
                    return
                }
                when(event.searchOrder.orderType) {
                    is OrderType.Ascending -> {
                        when(event.searchOrder) {
                            is SearchOrder.Price -> {
                                _state.value = _state.value.copy(
                                    partList = _state.value.partList.sortedBy {it.price },
                                    searchOrder = SearchOrder.Price(OrderType.Ascending)
                                )
                            }
                            is SearchOrder.DeliveryDate -> {
                                _state.value = _state.value.copy(
                                    partList = _state.value.partList.sortedByDescending { it.deliveryTime },
                                    searchOrder = SearchOrder.DeliveryDate(OrderType.Ascending)
                                )
                            }
                        }
                    }

                    is OrderType.Descending -> {
                        when(event.searchOrder) {
                            is SearchOrder.Price -> {
                                _state.value = _state.value.copy(
                                    partList = _state.value.partList.sortedByDescending { it.price },
                                    searchOrder = SearchOrder.Price(OrderType.Descending)
                                )
                            }
                            is SearchOrder.DeliveryDate -> {
                                _state.value = _state.value.copy(
                                    partList = _state.value.partList.sortedBy { it.deliveryTime },
                                    searchOrder = SearchOrder.DeliveryDate(OrderType.Descending)
                                )
                            }
                        }
                    }
                }
            }
            is SearchEvent.Filter -> {
                when(event.searchFilter) {
                    is SearchFilter.Price -> {
                        _state.value = _state.value.copy(
                            partList = _state.value.partList.filter {
                                it.price.toInt() > event.minPrice
                            }.filter {
                                it.price.toInt() < event.maxPrice
                            }
                        )
                        onEvent(SearchEvent.Order(event.searchFilter.searchOrder))
                    }
                }
            }
            is SearchEvent.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
            is SearchEvent.ToggleFilterSection -> {
                _state.value = _state.value.copy(
                    isFilterSectionVisible = !state.value.isFilterSectionVisible
                )
            }
        }
    }
}