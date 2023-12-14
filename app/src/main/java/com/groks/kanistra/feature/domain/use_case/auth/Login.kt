package com.groks.kanistra.feature.domain.use_case.auth

import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.data.remote.dto.LoginBody
import com.groks.kanistra.feature.domain.model.SimpleResponse
import com.groks.kanistra.feature.domain.repository.DataStoreRepository
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class Login @Inject constructor(
    private val kanistraRepository: KanistraRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(loginBody: LoginBody): Flow<Resource<SimpleResponse>> = flow {
        if (loginBody.phoneNumber.isBlank()){
            emit(Resource.Error("Login field is blank"))
            return@flow
        }
        if (loginBody.password.isBlank()){
            emit(Resource.Error("Password field is blank"))
            return@flow
        }
        try {
            emit(Resource.Loading())
            val simpleResponse = kanistraRepository.getToken(loginBody)
            dataStoreRepository.saveToken(simpleResponse.message)
            emit(Resource.Success(simpleResponse))

        } catch (e: HttpException){
            emit(Resource.Error(
                message = e.localizedMessage ?: "An unexpected error occurred")
            )
        } catch (e: IOException) {
            emit(Resource.Error(
                message = "Couldn't reach the server. Check your internet connection.")
            )
        }
    }
}