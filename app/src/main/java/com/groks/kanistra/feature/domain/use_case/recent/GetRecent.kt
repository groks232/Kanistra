package com.groks.kanistra.feature.domain.use_case.recent

import com.groks.kanistra.common.Resource
import com.groks.kanistra.feature.domain.model.RecentItem
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRecent @Inject constructor(
    private val repository: KanistraRepository
) {
    operator fun invoke(): Flow<Resource<List<RecentItem>>> = flow {
        try {
            emit(Resource.Loading())
            val recentItems = repository.getRecent()
            emit(Resource.Success(recentItems))
        } catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach the server. Check your internet connection."))
        }
    }
}