package com.groks.kanistra.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.groks.kanistra.common.Constants
import com.groks.kanistra.feature.data.data_source.HintDatabase
import com.groks.kanistra.feature.data.remote.KanistraApi
import com.groks.kanistra.feature.data.remote.auth.AuthInterceptor
import com.groks.kanistra.feature.data.repository.DataStoreRepositoryImpl
import com.groks.kanistra.feature.data.repository.HintRepositoryImpl
import com.groks.kanistra.feature.data.repository.KanistraRepositoryImpl
import com.groks.kanistra.feature.domain.repository.DataStoreRepository
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import com.groks.kanistra.feature.domain.use_case.auth.AuthUseCases
import com.groks.kanistra.feature.domain.use_case.auth.ForgotPassword
import com.groks.kanistra.feature.domain.use_case.auth.Login
import com.groks.kanistra.feature.domain.use_case.auth.Register
import com.groks.kanistra.feature.domain.use_case.cart.AddToCart
import com.groks.kanistra.feature.domain.use_case.cart.CartUseCases
import com.groks.kanistra.feature.domain.use_case.cart.DeleteCartItem
import com.groks.kanistra.feature.domain.use_case.cart.EditCartItem
import com.groks.kanistra.feature.domain.use_case.cart.GetCart
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //DataStore
    @Provides
    @Singleton
    fun provideDataStoreRepository(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepositoryImpl(context)
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(dataStoreRepository: DataStoreRepository): AuthInterceptor {
        return AuthInterceptor(dataStoreRepository)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    //Db
    @Provides
    @Singleton
    fun provideHintDatabase(app: Application): HintDatabase{
        return Room.databaseBuilder(
            app,
            HintDatabase::class.java,
            HintDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideHintRepository(db: HintDatabase): HintRepositoryImpl {
        return HintRepositoryImpl(db.hintDao)
    }

    //api
    @Provides
    @Singleton
    fun provideKanistraApi(okHttpClient: OkHttpClient): KanistraApi {
        /*val client =  OkHttpClient.Builder()
            .addInterceptor(OAuthInterceptor("Bearer", hint?.token ?: ""))
            .build()*/
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KanistraApi::class.java)
    }

    @Provides
    @Singleton
    fun provideKanistraRepository(api: KanistraApi): KanistraRepository {
        return KanistraRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideCartUseCases(repository: KanistraRepository): CartUseCases {
        return CartUseCases(
            addToCart = AddToCart(repository),
            deleteCartItem = DeleteCartItem(repository),
            editCartItem = EditCartItem(repository),
            getCart = GetCart(repository)
        )
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(kanistraRepository: KanistraRepository, dataStoreRepository: DataStoreRepository): AuthUseCases {
        return AuthUseCases(
            login = Login(kanistraRepository, dataStoreRepository),
            register = Register(kanistraRepository),
            forgotPassword = ForgotPassword(kanistraRepository)
        )
    }
}