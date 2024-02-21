package com.groks.kanistra.feature.domain.use_case.auth

import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.data.remote.dto.RegisterBody
import com.groks.kanistra.feature.domain.model.SimpleResponse
import com.groks.kanistra.feature.domain.repository.DataStoreRepository
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class Register @Inject constructor(
    private val kanistraRepository: KanistraRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(registerBody: RegisterBody): Flow<Resource<SimpleResponse>> = flow {
        if (registerBody.fullName.isBlank()){
            emit(Resource.Error("Name is blank"))
            return@flow
        }
        if (registerBody.phoneNumber.isBlank()){
        emit(Resource.Error("Phone is blank"))
        return@flow
    }
        if (registerBody.email.isBlank()){
            emit(Resource.Error("Email is blank"))
            return@flow
        }
        if (registerBody.password.isBlank()){
            emit(Resource.Error("Password field is blank"))
            return@flow
        }
        val register = RegisterBody(
            fullName = registerBody.fullName,
            phoneNumber = "8${registerBody.phoneNumber}",
            email = registerBody.email,
            password = registerBody.password
        )
        try {
            emit(Resource.Loading())
            val simpleResponse = kanistraRepository.register(register)
            dataStoreRepository.saveToken(simpleResponse.message)
            emit(Resource.Success(simpleResponse))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
        }
    }
}