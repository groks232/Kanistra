package com.groks.kanistra.feature.domain.use_case.auth

import com.groks.kanistra.feature.domain.repository.KanistraRepository
import javax.inject.Inject

class ForgotPassword @Inject constructor(
    private val repository: KanistraRepository
) {

}