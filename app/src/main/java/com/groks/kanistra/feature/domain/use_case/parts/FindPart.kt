package com.groks.kanistra.feature.domain.use_case.parts

import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.model.Part
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FindPart @Inject constructor(
    private val repository: KanistraRepository
) {
    operator fun invoke(id: String): Flow<Resource<Part>> = flow {
        try {
            emit(Resource.Loading())
            val part = repository.findPart(id)
            emit(Resource.Success(part))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
        }
    }
}