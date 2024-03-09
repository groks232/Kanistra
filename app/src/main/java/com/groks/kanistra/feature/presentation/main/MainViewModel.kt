package com.groks.kanistra.feature.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.feature.domain.use_case.cart.GetCartAmount
import com.groks.kanistra.feature.domain.use_case.cart.ReadCartAmount
import com.groks.kanistra.feature.domain.use_case.cart.WriteCartAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    readCartAmount: ReadCartAmount,
    getCartAmount: GetCartAmount,
    writeCartAmount: WriteCartAmount
): ViewModel() {
    val cartAmount = readCartAmount.invoke().stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = 0)

    init {
        getCartAmount().onEach {
            writeCartAmount(it)
        }.retryWhen { cause, _ ->
            if (cause is HttpException) {
                delay(400)
                return@retryWhen true
            } else {
                return@retryWhen false
            }
        }.catch {
            // Update Error UI
        }.launchIn(viewModelScope)
    }
}