package com.groks.kanistra.feature.presentation.search

import com.groks.kanistra.feature.domain.model.Hint

data class HintState(
    val isLoading: Boolean = false,
    val hintList: List<Hint> = emptyList(),
    val error: String = ""
)