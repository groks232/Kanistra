package com.groks.kanistra.feature.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.common.ViewState
import com.groks.kanistra.feature.domain.use_case.cart.CartUseCases
import com.groks.kanistra.feature.domain.use_case.cart.WriteCartAmount
import com.groks.kanistra.feature.domain.use_case.favorites.FavoritesUseCases
import com.groks.kanistra.feature.domain.use_case.hint.HintUseCases
import com.groks.kanistra.feature.domain.use_case.main.CheckToken
import com.groks.kanistra.feature.domain.use_case.parts.FindParts
import com.groks.kanistra.feature.domain.util.OrderType
import com.groks.kanistra.feature.domain.util.SearchFilter
import com.groks.kanistra.feature.domain.util.SearchOrder
import com.groks.kanistra.feature.presentation.auth.AuthTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val findParts: FindParts,
    private val cartUseCases: CartUseCases,
    private val hintUseCases: HintUseCases,
    private val favoritesUseCases: FavoritesUseCases,
    checkToken: CheckToken,
    private val writeCartAmount: WriteCartAmount
) : ViewModel() {
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private val _searchFieldText = mutableStateOf(
        AuthTextFieldState(hint = "Enter password")
    )
    val searchFieldText: State<AuthTextFieldState> = _searchFieldText

    private val _hintState = mutableStateOf(HintState())
    val hintState: State<HintState> = _hintState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val viewState = checkToken.invoke().map { loggedIn ->
        when (loggedIn) {
            true -> {
                ViewState.LoggedIn
            }

            false -> {
                ViewState.NotLoggedIn
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = ViewState.Loading
    )


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.EnteredPartName -> {
                _searchFieldText.value = searchFieldText.value.copy(
                    text = event.value
                )
            }

            is SearchEvent.AddToCart -> {
                cartUseCases.addToCart(cartItem = event.cartItem).onEach { result ->
                    when (result) {
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
                                    searchEvent = event
                                )
                            )
                        }

                        is Resource.Loading -> {

                        }
                    }
                }.launchIn(viewModelScope)

                cartUseCases.getCartAmount().onEach {
                    writeCartAmount(it)
                }.retryWhen { cause, _ ->
                    if (cause is HttpException) {
                        delay(400)
                        return@retryWhen true
                    } else {
                        return@retryWhen false
                    }
                }.launchIn(viewModelScope)
            }

            is SearchEvent.AddToFavorites -> {
                favoritesUseCases.addToFavorites(
                    favoritesItem = event.favoritesItem
                ).onEach { result ->
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
                                    searchEvent = event
                                )
                            )
                        }
                        is Resource.Loading -> {

                        }
                    }
                }.launchIn(viewModelScope)
            }

            is SearchEvent.Search -> {
                findParts(searchFieldText.value.text).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = SearchState(
                                partList = result.data ?: emptyList(),
                                modifiedPartList = result.data ?: emptyList()
                            )
                            onEvent(SearchEvent.Order(SearchOrder.Price(OrderType.Descending)))
                        }

                        is Resource.Error -> {
                            _state.value = SearchState(
                                error = result.message ?: "unexpected error occurred"
                            )
                            _eventFlow.emit(
                                UiEvent.ShowErrorSnackbar(
                                    message = result.message ?: "Unexpected error occurred.",
                                    searchEvent = event
                                )
                            )
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
                when (event.searchOrder.orderType) {
                    is OrderType.Ascending -> {
                        when (event.searchOrder) {
                            is SearchOrder.Price -> {
                                _state.value = _state.value.copy(
                                    modifiedPartList = _state.value.modifiedPartList.sortedBy { it.price },
                                    partList = _state.value.partList.sortedBy { it.price },
                                    searchOrder = SearchOrder.Price(OrderType.Ascending)
                                )
                            }

                            is SearchOrder.DeliveryDate -> {
                                _state.value = _state.value.copy(
                                    modifiedPartList = _state.value.modifiedPartList.sortedByDescending { it.deliveryTime },
                                    partList = _state.value.partList.sortedByDescending { it.deliveryTime },
                                    searchOrder = SearchOrder.DeliveryDate(OrderType.Ascending)
                                )
                            }
                        }
                    }

                    is OrderType.Descending -> {
                        when (event.searchOrder) {
                            is SearchOrder.Price -> {
                                _state.value = _state.value.copy(
                                    modifiedPartList = _state.value.modifiedPartList.sortedByDescending { it.price },
                                    partList = _state.value.partList.sortedByDescending { it.price },
                                    searchOrder = SearchOrder.Price(OrderType.Descending)
                                )
                            }

                            is SearchOrder.DeliveryDate -> {
                                _state.value = _state.value.copy(
                                    modifiedPartList = _state.value.modifiedPartList.sortedBy { it.deliveryTime },
                                    partList = _state.value.partList.sortedBy { it.deliveryTime },
                                    searchOrder = SearchOrder.DeliveryDate(OrderType.Descending)
                                )
                            }
                        }
                    }
                }
            }

            is SearchEvent.Filter -> {
                when (event.searchFilter) {
                    is SearchFilter.Price -> {
                        _state.value = _state.value.copy(
                            modifiedPartList = _state.value.partList
                        )
                        _state.value = _state.value.copy(
                            modifiedPartList = _state.value.modifiedPartList.filter { part ->
                                part.price.toInt() > (event.searchFilter.minimal
                                    ?: _state.value.partList.minOf { it.deliveryTime })
                            }.filter { part ->
                                part.price.toInt() < (event.searchFilter.maximal
                                    ?: _state.value.partList.maxOf { it.deliveryTime })
                            }
                        )
                    }

                    is SearchFilter.DeliveryDate -> {
                        _state.value = _state.value.copy(
                            modifiedPartList = _state.value.partList.filter { part ->
                                part.deliveryTime > (event.searchFilter.minimal
                                    ?: _state.value.partList.minOf { part.deliveryTime })
                            }.filter { part ->
                                part.deliveryTime < (event.searchFilter.maximal
                                    ?: _state.value.partList.maxOf { it.deliveryTime })
                            }
                        )
                    }
                }
            }

            is SearchEvent.ResetFilters -> {
                _state.value = _state.value.copy(
                    modifiedPartList = _state.value.partList,
                    searchOrder = SearchOrder.Price(OrderType.Ascending),
                    searchFilter = SearchFilter.Price(null, null)
                )
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

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        data class ShowErrorSnackbar(val message: String, val searchEvent: SearchEvent) : UiEvent()
    }
}