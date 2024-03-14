package com.groks.kanistra.feature.presentation.profile

import com.groks.kanistra.feature.domain.model.RecentItem
import com.groks.kanistra.feature.domain.model.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = "",
    val recentList: List<RecentItem> = emptyList(),
)
