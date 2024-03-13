package com.groks.kanistra.feature.domain.use_case.recent

data class RecentUseCases(
    val addToRecent: AddToRecent,
    val deleteRecentItem: DeleteRecentItem,
    val getRecent: GetRecent
)
