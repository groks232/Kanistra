package com.groks.kanistra.feature.presentation.part_details

import com.groks.kanistra.feature.domain.model.Part

data class PartDetailsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val part: Part?
)