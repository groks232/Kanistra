package com.groks.kanistra.feature.presentation.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.use_case.cart.CartUseCases
import com.groks.kanistra.feature.presentation.search.SearchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartUseCases: CartUseCases
) : ViewModel() {
    private val _state = mutableStateOf(CartState())
    val state: State<CartState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        onEvent(CartEvent.GetCart)
    }

    fun onEvent(event: CartEvent) {
        when(event){
            is CartEvent.GetCart -> {
                cartUseCases.getCart().onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = CartState(cartList = (result.data ?: mutableListOf()).toMutableList())
                        }

                        is Resource.Error -> {
                            _state.value =
                                CartState(error = result.message ?: "An unexpected error occurred.")
                        }

                        is Resource.Loading -> {
                            _state.value = CartState(isLoading = true)
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is CartEvent.DeleteCartItem -> {
                viewModelScope.launch {
                    cartUseCases.deleteCartItem(
                        event.cartItem
                    )
                }
                _state.value = _state.value.copy(cartList = _state.value.cartList.filter {
                    it.id != event.cartItem.id
                }.toMutableList())

                viewModelScope.launch {
                    cartUseCases.getCartAmount()
                }
            }
            is CartEvent.RestoreCartItem -> {

            }
            is CartEvent.RefreshCart -> {
                viewModelScope.launch {
                    _isRefreshing.value = true
                    cartUseCases.getCart().onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                _state.value = CartState(cartList = (result.data ?: mutableListOf()).toMutableList())
                                _isRefreshing.value = false
                            }

                            is Resource.Error -> {
                                _state.value =
                                    CartState(error = result.message ?: "An unexpected error occurred.")
                                _isRefreshing.value = false
                            }

                            is Resource.Loading -> {
                                //_state.value = CartState(isLoading = true)
                            }
                        }
                    }.launchIn(viewModelScope)
                    //delay(1000L)
                }
            }
            is CartEvent.IncreaseAmount -> {
                if (event.cartItem.amount < 100){
                    viewModelScope.launch {
                        cartUseCases.editCartItem(event.cartItem)
                    }
                }
            }
            is CartEvent.DecreaseAmount -> {
                if (event.cartItem.amount > 0) {
                    viewModelScope.launch {
                        cartUseCases.editCartItem(event.cartItem)
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        data class ShowErrorSnackbar(val message: String, val searchEvent: SearchEvent): UiEvent()
    }
}