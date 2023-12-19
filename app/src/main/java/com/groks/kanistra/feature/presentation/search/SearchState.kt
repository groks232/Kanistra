package com.groks.kanistra.feature.presentation.search

import com.groks.kanistra.feature.domain.model.Part

data class SearchState(
    val isLoading: Boolean = false,
    val partList: List<Part> = emptyList(),
    val error: String = ""
)
