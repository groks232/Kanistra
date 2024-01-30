package com.groks.kanistra.feature.domain.use_case.hint

data class HintUseCases(
    val getHints: GetHints,
    val deleteHint: DeleteHint,
    val insertHint: InsertHint
)
