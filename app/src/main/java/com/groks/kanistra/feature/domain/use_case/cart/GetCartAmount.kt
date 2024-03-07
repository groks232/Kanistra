package com.groks.kanistra.feature.domain.use_case.cart

import android.util.Log
import com.groks.kanistra.feature.domain.repository.DataStoreRepository
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCartAmount @Inject constructor(
    private val kanistraRepository: KanistraRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke() {
        try {
            val cartAmount = kanistraRepository.getCartAmount()
            dataStoreRepository.saveCartCount(cartAmount)
        } catch (e: HttpException){
            Log.d("Cart amount exception", e.message())
        } catch (e: IOException) {
            Log.d("Cart amount exception", "Internet connection problems")
        }
    }
}