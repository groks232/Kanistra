package com.groks.kanistra.feature.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.data.remote.dto.LoginBody
import com.groks.kanistra.feature.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
): ViewModel() {
    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    private val _loginFieldText = mutableStateOf(AuthTextFieldState(
        hint = "Enter login"
    ))
    val loginFieldText: State<AuthTextFieldState> = _loginFieldText

    private val _passwordFieldText = mutableStateOf(AuthTextFieldState(
        hint = "Enter password"
    ))
    val passwordFieldText: State<AuthTextFieldState> = _passwordFieldText

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun clearState(){
        _passwordFieldText.value = AuthTextFieldState()
        _loginFieldText.value = AuthTextFieldState()
    }

    fun onEvent(event: AuthEvent){
        when(event){
            is AuthEvent.EnteredLogin -> {
                _loginFieldText.value = loginFieldText.value.copy(
                    text = event.value
                )
            }
            is AuthEvent.EnteredPassword -> {
                _passwordFieldText.value = passwordFieldText.value.copy(
                    text = event.value
                )
            }
            is AuthEvent.Login -> {
                viewModelScope.launch {
                    authUseCases.login(
                        LoginBody(
                            phoneNumber = loginFieldText.value.text,
                            password = passwordFieldText.value.text
                        )
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                _state.value = AuthState()
                                _eventFlow.emit(UiEvent.Login)
                            }

                            is Resource.Error -> {
                                _state.value = AuthState(
                                    error = result.message ?: "unexpected error occurred"
                                )

                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        message = result.message ?: "Unexpected error occurred."
                                    )
                                )
                            }

                            is Resource.Loading -> {
                                _state.value = AuthState(isLoading = true)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
            is AuthEvent.ForgotPassword -> {
                /*TODO("Not implemented yet")*/
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object Login: UiEvent()
    }
}