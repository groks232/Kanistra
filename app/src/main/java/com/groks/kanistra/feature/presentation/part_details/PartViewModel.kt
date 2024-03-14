package com.groks.kanistra.feature.presentation.part_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.model.RecentItem
import com.groks.kanistra.feature.domain.use_case.cart.CartUseCases
import com.groks.kanistra.feature.domain.use_case.parts.FindPart
import com.groks.kanistra.feature.domain.use_case.recent.RecentUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartViewModel @Inject constructor(
    private val cartUseCases: CartUseCases,
    private val findPart: FindPart,
    private val recentUseCases: RecentUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(PartDetailsState(part = null))
    val state: State<PartDetailsState> = _state
    init {
        val id = savedStateHandle.get<String>("id")
        val provider = savedStateHandle.get<String>("provider")
        findPart(
            id = id!!,
            provider = provider!!
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PartDetailsState(part = result.data)

                    recentUseCases.addToRecent(
                        RecentItem(
                            id = "",
                            partId = id,
                            title = _state.value.part?.title ?: "",
                            brand = _state.value.part?.brand ?: "",
                            price = _state.value.part?.price ?: 0.0,
                            image = if (_state.value.part?.images?.isNotEmpty() == true) {
                                if (_state.value.part?.provider == "VAvto") "https://static.v-avto.ru${_state.value.part?.images!![0]}"
                                else _state.value.part!!.images[0]
                            } else "",
                            provider = provider,
                            creationDate = "",
                            isInCart = null
                        )
                    ).launchIn(viewModelScope)
                }

                is Resource.Error -> {
                    _state.value = PartDetailsState(
                        error = result.message ?: "unexpected error occurred",
                        part = null
                    )
                }

                is Resource.Loading -> {
                    _state.value = PartDetailsState(isLoading = true, part = null)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: PartEvent) {
        when(event) {
            is PartEvent.AddItemToCart -> {
                viewModelScope.launch {
                    cartUseCases.addToCart(
                        event.cartItem
                    )
                }
            }
            is PartEvent.DeleteItemFromCart -> {
                viewModelScope.launch {
                    cartUseCases.deleteCartItem(
                        event.cartItem
                    )
                }
            }
            is PartEvent.EditItem -> {
                viewModelScope.launch {
                    cartUseCases.editCartItem(
                        event.cartItem
                    )
                }
            }
            is PartEvent.RestoreCartItem -> {

            }
        }
    }
}