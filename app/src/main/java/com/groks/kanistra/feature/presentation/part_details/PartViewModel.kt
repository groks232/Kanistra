package com.groks.kanistra.feature.presentation.part_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.feature.domain.use_case.cart.CartUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PartViewModel @Inject constructor(
    private val cartUseCases: CartUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(String())
    val state: State<String> = _state
    init {
        savedStateHandle.get<String>("id")?.let {id ->
            viewModelScope.launch {
                _state.value = id
                /*TODO("Not implemented yet(getPartById)")*/
            }
        }
    }
}