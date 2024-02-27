package com.groks.kanistra.feature.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.feature.domain.use_case.cart.GetCartAmount
import com.groks.kanistra.feature.domain.use_case.cart.ReadCartAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    readCartAmount: ReadCartAmount,
    getCartAmount: GetCartAmount
): ViewModel() {
    val cartAmount = readCartAmount.invoke().stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = 0)

    init {
        viewModelScope.launch {
            getCartAmount()
        }
    }
}