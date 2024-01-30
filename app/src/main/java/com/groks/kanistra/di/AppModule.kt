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
import com.groks.kanistra.feature.domain.repository.HintRepository
import com.groks.kanistra.feature.domain.repository.KanistraRepository
import com.groks.kanistra.feature.domain.use_case.auth.AuthUseCases
import com.groks.kanistra.feature.domain.use_case.auth.ForgotPassword
import com.groks.kanistra.feature.domain.use_case.auth.LogOut
import com.groks.kanistra.feature.domain.use_case.auth.Login
import com.groks.kanistra.feature.domain.use_case.auth.Register
import com.groks.kanistra.feature.domain.use_case.cart.AddToCart
import com.groks.kanistra.feature.domain.use_case.cart.CartUseCases
import com.groks.kanistra.feature.domain.use_case.cart.DeleteCartItem
import com.groks.kanistra.feature.domain.use_case.cart.EditCartItem
import com.groks.kanistra.feature.domain.use_case.cart.GetCart
import com.groks.kanistra.feature.domain.use_case.favorites.AddToFavorites
import com.groks.kanistra.feature.domain.use_case.favorites.DeleteFavoritesItem
import com.groks.kanistra.feature.domain.use_case.favorites.FavoritesUseCases
import com.groks.kanistra.feature.domain.use_case.favorites.GetFavorites
import com.groks.kanistra.feature.domain.use_case.hint.DeleteHint
import com.groks.kanistra.feature.domain.use_case.hint.GetHints
import com.groks.kanistra.feature.domain.use_case.hint.HintUseCases
import com.groks.kanistra.feature.domain.use_case.hint.InsertHint
import com.groks.kanistra.feature.domain.use_case.user.DeleteUser
import com.groks.kanistra.feature.domain.use_case.user.EditUserInfo
import com.groks.kanistra.feature.domain.use_case.user.GetUserInfo
import com.groks.kanistra.feature.domain.use_case.user.UserUseCases
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
    fun provideHintRepository(db: HintDatabase): HintRepository {
        return HintRepositoryImpl(db.hintDao)
    }

    //api
    @Provides
    @Singleton
    fun provideKanistraApi(okHttpClient: OkHttpClient): KanistraApi {
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
            forgotPassword = ForgotPassword(kanistraRepository),
            logOut = LogOut(dataStoreRepository)
        )
    }

    @Provides
    @Singleton
    fun provideUserUseCases(repository: KanistraRepository): UserUseCases {
        return UserUseCases(
            deleteUser = DeleteUser(repository),
            editUserInfo = EditUserInfo(repository),
            getUserInfo = GetUserInfo(repository)
        )
    }

    @Provides
    @Singleton
    fun provideFavoritesUseCases(repository: KanistraRepository): FavoritesUseCases {
        return FavoritesUseCases(
            addToFavorites = AddToFavorites(repository),
            deleteFavoritesItem = DeleteFavoritesItem(repository),
            getFavorites = GetFavorites(repository)
        )
    }

    @Provides
    @Singleton
    fun provideHintUseCases(repository: HintRepository): HintUseCases {
        return HintUseCases(
            getHints = GetHints(repository),
            deleteHint = DeleteHint(repository),
            insertHint = InsertHint(repository)
        )
    }
}