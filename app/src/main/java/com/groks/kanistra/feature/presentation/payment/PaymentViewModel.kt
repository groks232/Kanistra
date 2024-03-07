package com.groks.kanistra.feature.presentation.payment

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(

): ViewModel() {
    private val _state = mutableStateOf(PaymentState())
    val state: State<PaymentState> = _state

    init {
        proceed()
    }

    private fun proceed(){
        viewModelScope.launch {
            _state.value = PaymentState(isLoading = true)
            delay(5000L)
            _state.value = PaymentState(isLoading = false, someData = "Success")
        }
    }
}