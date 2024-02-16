package com.groks.kanistra.feature.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.data.remote.dto.RegisterBody
import com.groks.kanistra.feature.domain.use_case.auth.Register
import com.groks.kanistra.feature.presentation.auth.AuthTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val register: Register
): ViewModel() {

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    private val _nameFieldText = mutableStateOf(
        AuthTextFieldState(
        hint = "Введите имя и фамилию"
    )
    )
    val nameFieldText: State<AuthTextFieldState> = _nameFieldText

    private val _emailFieldText = mutableStateOf(
        AuthTextFieldState(
        hint = "Введите email адрес"
    )
    )
    val emailFieldText: State<AuthTextFieldState> = _emailFieldText

    private val _phoneFieldText = mutableStateOf(
        AuthTextFieldState(
            hint = "Введите номер телефона"
        )
    )
    val phoneFieldText: State<AuthTextFieldState> = _phoneFieldText

    private val _passwordFieldText = mutableStateOf(
        AuthTextFieldState(
            hint = "Введите пароль"
        )
    )
    val passwordFieldText: State<AuthTextFieldState> = _passwordFieldText

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: RegisterEvent){
        when(event) {
            is RegisterEvent.EnteredEmail -> {
                _emailFieldText.value = emailFieldText.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredName -> {
                _nameFieldText.value = nameFieldText.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredPassword -> {
                _passwordFieldText.value = passwordFieldText.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.EnteredPhoneNumber -> {
                _phoneFieldText.value = phoneFieldText.value.copy(
                    text = event.value
                )
            }
            is RegisterEvent.Register -> {
                viewModelScope.launch {
                    register(
                        RegisterBody(
                            fullName = nameFieldText.value.text,
                            phoneNumber = phoneFieldText.value.text,
                            emailAddress = emailFieldText.value.text,
                            password = passwordFieldText.value.text
                        )
                    ).onEach { result ->
                        when (result) {
                            is Resource.Success -> {
                                _state.value = RegisterState()
                                _eventFlow.emit(UiEvent.Register)
                            }

                            is Resource.Error -> {
                                _state.value = RegisterState(
                                    error = result.message ?: "unexpected error occurred"
                                )
                                _eventFlow.emit(
                                    UiEvent.ShowSnackbar(
                                        message = result.message ?: "Unexpected error occurred."
                                    )
                                )
                            }

                            is Resource.Loading -> {
                                _state.value = RegisterState(isLoading = true)
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object Register: UiEvent()
    }
}