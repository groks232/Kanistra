package com.groks.kanistra.feature.domain.use_case.user

import com.groks.kanistra.feature.domain.model.SimpleResponse
import com.groks.kanistra.feature.domain.model.User
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import javax.inject.Inject

class DeleteUser @Inject constructor(
    private val repository: KanistraRepository
) {
    suspend operator fun invoke(user: User): SimpleResponse {
        return repository.deleteUser(user)
    }
}