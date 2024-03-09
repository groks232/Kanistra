package com.groks.kanistra.feature.presentation.profile_data

import com.groks.kanistra.feature.domain.model.User

data class ProfileDataState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)
