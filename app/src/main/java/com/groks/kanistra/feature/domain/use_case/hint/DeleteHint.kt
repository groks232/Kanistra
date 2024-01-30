package com.groks.kanistra.feature.domain.use_case.hint

import com.groks.kanistra.feature.domain.repository.HintRepository
import javax.inject.Inject

class DeleteHint @Inject constructor(
    private val repository: HintRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteHint(id)
    }
}