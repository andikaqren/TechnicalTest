package com.example.core.business.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.example.core.business.data.local.model.LocalGenre
import com.example.core.business.data.local.model.LocalMovie
import com.example.core.business.data.local.model.LocalTv

@Dao
interface LocalDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedMovie(movie:LocalMovie): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSelectedTV(movie:LocalTv): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertSelectedGenres(genres: List<LocalGenre>)

    @Query("DELETE FROM GENRE")
    fun deleteGenre()

    @Delete()
    fun deleteMovie(movie: LocalMovie)

    @Delete()
    fun deleteTV(movie: LocalTv)

    @Query("SELECT * FROM MOVIE WHERE id = :id")
    fun getSelectedMovie(id: Int):LocalMovie

    @Query("SELECT * FROM TV WHERE id = :id")
    fun getSelectedTV(id: Int):LocalTv

    @Query("SELECT * FROM MOVIE")
    fun getAllFavouriteMovie(): PagingSource<Int, LocalMovie>

    @Query("SELECT * FROM TV")
    fun getAllFavouriteTV(): PagingSource<Int,LocalTv>

    @Query("SELECT * FROM Genre")
    fun getGenres(): List<LocalGenre>
}