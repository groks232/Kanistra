package com.groks.kanistra.feature.domain.use_case.auth

import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.data.remote.dto.LoginBody
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
    operator fun invoke(loginBody: LoginBody): Flow<Resource<String>> = flow {
        if (loginBody.phoneNumber.isBlank()) {
            emit(
                Resource.Error(
                    "Введите номер телефона",
                )
            )
            return@flow
        }
        if (loginBody.password.isBlank()) {
            emit(Resource.Error("Введите пароль"))
            return@flow
        }
        val login = LoginBody(
            phoneNumber = "8${loginBody.phoneNumber}",
            password = loginBody.password
        )

        try {
            emit(Resource.Loading())
            val token = kanistraRepository.getToken(login)
            dataStoreRepository.saveToken(token.string())
            emit(Resource.Success("simpleResponse"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.response()?.errorBody()?.string() ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
        }
    }
}