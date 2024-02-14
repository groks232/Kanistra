package com.groks.kanistra.feature.presentation.order

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.use_case.cart.GetMultipleCartItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMultipleCartItems: GetMultipleCartItems
): ViewModel() {
    private val _state = mutableStateOf(OrderState())
    val state: State<OrderState> = _state

    init {
        val ids = savedStateHandle.get<String>("ids") ?: ""
        onEvent(OrderEvent.GetMultipleCartItems(ids))
    }

    fun onEvent(event: OrderEvent){
        when(event){
            is OrderEvent.GetMultipleCartItems -> {
                getMultipleCartItems(event.ids).onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = OrderState(orderList = (result.data ?: mutableListOf()).toMutableList())
                        }

                        is Resource.Error -> {
                            _state.value =
                                OrderState(error = result.message ?: "An unexpected error occurred.")
                        }

                        is Resource.Loading -> {
                            _state.value = OrderState(isLoading = true)
                        }
                    }
                }.launchIn(viewModelScope)
            }
            is OrderEvent.AgreeToRules -> {
                _state.value = _state.value.copy(agreeToRules = !_state.value.agreeToRules)
            }
        }
    }
}