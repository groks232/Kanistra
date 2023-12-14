package com.groks.kanistra.common

sealed class Resource<T>(val data: T? = null, val message: String? = null){
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}

sealed class ViewState {
    object Loading: ViewState() // hasLoggedIn = unknown
    object LoggedIn: ViewState() // hasLoggedIn = true
    object NotLoggedIn: ViewState() // hasLoggedIn = false
}

sealed class StateView {
    object LoggedIn: StateView() // hasLoggedIn = true
    object NotLoggedIn: StateView() // hasLoggedIn = false
}