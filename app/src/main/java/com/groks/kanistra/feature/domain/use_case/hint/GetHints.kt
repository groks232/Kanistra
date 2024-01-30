package com.groks.kanistra.feature.domain.use_case.hint

import com.groks.kanistra.feature.domain.model.Hint
import com.groks.kanistra.feature.domain.repository.HintRepository
import javax.inject.Inject

class GetHints @Inject constructor(
    private val repository: HintRepository
) {
    suspend operator fun invoke(): List<Hint> {
        return repository.getHints()
    }
}