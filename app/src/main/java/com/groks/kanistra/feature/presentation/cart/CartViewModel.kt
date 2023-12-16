package com.groks.kanistra.feature.presentation.cart

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.use_case.cart.CartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        getCart()
    }

    fun onEvent(event: CartEvent) {
        when(event){
            is CartEvent.DeleteCartItem -> {
                viewModelScope.launch {
                    cartUseCases.deleteCartItem(
                        event.cartItem
                    )
                }

            }
            is CartEvent.RestoreCartItem -> {

            }
        }
    }

    private fun getCart() {
        cartUseCases.getCart().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CartState(cartList = result.data ?: emptyList())
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
}