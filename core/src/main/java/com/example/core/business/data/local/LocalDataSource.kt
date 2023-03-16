package com.example.core.business.data.local

import androidx.paging.PagingData
import com.example.core.business.data.local.model.LocalGenre
import com.example.core.business.data.local.model.LocalMovie
import com.example.core.business.data.local.model.LocalTv
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertSelectedMovie(movie: LocalMovie): Long
    suspend fun insertSelectedTV(movie: LocalTv): Long
    suspend fun removeSelectedMovie(movie: LocalMovie)
    suspend fun removeSelectedTV(movie: LocalTv)
    suspend fun insertGenres(genres: List<LocalGenre>)
    fun getSelectedMovie(id: Int): LocalMovie
    fun getSelectedTV(id: Int): LocalTv
    fun getAllFavMovies(): Flow<PagingData<LocalMovie>>
    fun getAllFavTV(): Flow<PagingData<LocalTv>>
    fun getGenres(): List<LocalGenre>
}