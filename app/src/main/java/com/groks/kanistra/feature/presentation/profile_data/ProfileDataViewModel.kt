package com.groks.kanistra.feature.presentation.profile_data

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.common.ViewState
import com.groks.kanistra.feature.domain.use_case.auth.AuthUseCases
import com.groks.kanistra.feature.domain.use_case.main.CheckToken
import com.groks.kanistra.feature.domain.use_case.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDataViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val authUseCases: AuthUseCases,
    private val checkToken: CheckToken
):ViewModel() {
    private val _state = mutableStateOf(ProfileDataState())
    val state: State<ProfileDataState> = _state

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

    init {
        onEvent(ProfileDataEvent.GetUserData)
    }

    fun onEvent(event: ProfileDataEvent) {
        when(event){
            is ProfileDataEvent.LogOut -> {
                viewModelScope.launch {
                    authUseCases.logOut()
                }
            }
            is ProfileDataEvent.GetUserData -> {
                userUseCases.getUserInfo().onEach { result ->
                    when(result){
                        is Resource.Loading -> {
                            _state.value = ProfileDataState(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            _state.value = ProfileDataState(
                                isLoading = false,
                                user = result.data
                            )
                        }
                        is Resource.Error -> {
                            _state.value = ProfileDataState(
                                isLoading = false,
                                error = result.message ?: "Unexpected error occurred"
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}