package com.groks.kanistra.feature.domain.use_case.recent

import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.model.RecentItem
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddToRecent @Inject constructor(
    private val repository: KanistraRepository
) {
    operator fun invoke(recentItem: RecentItem): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.addToRecent(recentItem)
            emit(Resource.Success(result.string()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.response()?.errorBody()?.string() ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
        }
    }
}