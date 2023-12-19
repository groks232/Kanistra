package com.groks.kanistra.feature.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.use_case.parts.FindParts
import com.groks.kanistra.feature.presentation.auth.AuthTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val findParts: FindParts
): ViewModel(){
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private val _searchFieldText = mutableStateOf(
        AuthTextFieldState(hint = "Enter password")
    )
    val searchFieldText: State<AuthTextFieldState> = _searchFieldText

    fun onEvent(event: SearchEvent) {
        when(event){
            is SearchEvent.EnteredPartName -> {
                _searchFieldText.value = searchFieldText.value.copy(
                    text = event.value
                )
            }
            is SearchEvent.AddToCart -> {

            }
            is SearchEvent.AddToFavorites -> {

            }
            is SearchEvent.Search -> {
                findParts(searchFieldText.value.text).onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = SearchState(
                                partList = result.data ?: emptyList()
                            )
                        }

                        is Resource.Error -> {
                            _state.value = SearchState(
                                error = result.message ?: "unexpected error occurred"
                            )
                            /*_eventFlow.emit(
                                AuthViewModel.UiEvent.ShowSnackbar(
                                    message = result.message ?: "Unexpected error occurred."
                                )
                            )*/
                        }

                        is Resource.Loading -> {
                            _state.value = SearchState(isLoading = true)
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }
}