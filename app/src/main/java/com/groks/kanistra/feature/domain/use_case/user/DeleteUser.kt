package com.groks.kanistra.feature.domain.use_case.user

import com.groks.kanistra.feature.domain.model.User
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class DeleteUser @Inject constructor(
    private val repository: KanistraRepository
) {
    suspend operator fun invoke(user: User): ResponseBody {
        return repository.deleteUser(user)
    }
}