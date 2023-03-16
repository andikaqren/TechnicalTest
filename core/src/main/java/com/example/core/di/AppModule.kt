package com.example.core.di

import com.example.core.business.data.local.LocalDataSource
import com.example.core.business.data.remote.RemoteDataSource
import com.example.core.business.interactors.ProductInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getInteractors(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): ProductInteractors {
        return ProductInteractors(localDataSource, remoteDataSource)
    }
}