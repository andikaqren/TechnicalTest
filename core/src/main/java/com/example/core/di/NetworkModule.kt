package com.example.core.di

import com.example.core.business.data.remote.RemoteDataSource
import com.example.core.business.data.remote.RemoteDataSourceImpl
import com.example.core.business.domain.utils.AppConstant
import com.example.core.business.network.NetworkManager
import com.example.core.business.network.NetworkManagerService
import com.example.core.business.network.NetworkManagerServiceImpl
import com.facebook.shimmer.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun providesInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Singleton
    @Provides
    fun providesOkHttp(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor = interceptor).build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient ,gson: Gson): NetworkManager {
        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstant.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        return  retrofit.create(NetworkManager::class.java)

    }

    @Singleton
    @Provides
    fun getNetworkManagerService(networkManager: NetworkManager): NetworkManagerService {
        return NetworkManagerServiceImpl(networkManager)
    }

    @Singleton
    @Provides
    fun getRemoteDataSource(networkManagerService: NetworkManagerService): RemoteDataSource {
        return RemoteDataSourceImpl(networkManagerService)
    }
}