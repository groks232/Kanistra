package com.groks.kanistra.feature.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.use_case.auth.AuthUseCases
import com.groks.kanistra.feature.domain.use_case.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val authUseCases: AuthUseCases
): ViewModel() {
    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state
    init {
        getUserData()
    }

    fun logOut(){
        viewModelScope.launch {
            authUseCases.logOut()
        }
    }

    private fun getUserData(){
        userUseCases.getUserInfo().onEach { result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = ProfileState(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = ProfileState(
                        isLoading = false,
                        user = result.data
                    )
                }
                is Resource.Error -> {
                    _state.value = ProfileState(
                        isLoading = false,
                        error = result.message ?: "Unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object Login: UiEvent()
    }
}