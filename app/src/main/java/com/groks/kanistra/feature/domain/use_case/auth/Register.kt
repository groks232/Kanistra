package com.groks.kanistra.feature.domain.use_case.auth

import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.data.remote.dto.RegisterBody
import com.groks.kanistra.feature.domain.model.SimpleResponse
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class Register @Inject constructor(
    private val repository: KanistraRepository
) {
    operator fun invoke(registerBody: RegisterBody): Flow<Resource<SimpleResponse>> = flow {
        try {
            emit(Resource.Loading())
            val simpleResponse = repository.register(registerBody)
            emit(Resource.Success(simpleResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
        }
    }
}