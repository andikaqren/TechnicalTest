package com.example.favourite.di

import android.content.Context
import com.example.core.di.AppModule
import com.example.core.di.FavouriteModule
import com.example.favourite.FragmentFavourite
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Component(dependencies = [FavouriteModule::class])
interface FavouriteComponent {

    @ExperimentalCoroutinesApi
    fun inject(fragment:FragmentFavourite)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favModule: FavouriteModule): Builder
        fun build(): FavouriteComponent
    }
}