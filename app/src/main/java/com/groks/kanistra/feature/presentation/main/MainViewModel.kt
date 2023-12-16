package com.groks.kanistra.feature.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.ViewState
import com.groks.kanistra.feature.domain.use_case.main.CheckToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    checkToken: CheckToken
): ViewModel() {
    val viewState = checkToken.invoke().map { loggedIn ->
        when(loggedIn){
            true -> {
                ViewState.LoggedIn
            }
            false -> {
                ViewState.NotLoggedIn
            }
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = ViewState.Loading)
}