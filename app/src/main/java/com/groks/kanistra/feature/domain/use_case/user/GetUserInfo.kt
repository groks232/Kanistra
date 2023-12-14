package com.groks.kanistra.feature.domain.use_case.user

import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.model.User
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserInfo @Inject constructor(
    private val repository: KanistraRepository
) {
    operator fun invoke(): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val user = repository.getUserInfo()
            emit(Resource.Success(user))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
        }
    }
}