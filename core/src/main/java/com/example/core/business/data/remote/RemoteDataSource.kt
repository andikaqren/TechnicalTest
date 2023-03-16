package com.example.core.business.data.remote

import com.example.core.business.data.remote.model.*

interface RemoteDataSource {
    suspend fun getTopMovies(page: Int): RemoteMovies
    suspend fun getDetailMovie(id: String): RemoteMovie
    suspend fun getSimilar(filename: Int?): RemoteMovies
    suspend fun getSimilarTV(filename: Int?): RemoteTvs
    suspend fun getRecomendation(filename: Int?): RemoteMovies
    suspend fun getRecomendationTV(filename: Int?): RemoteTvs
    suspend fun getNowPlaying(page: Int): RemoteMovies
    suspend fun getUpcoming(page: Int): RemoteMovies
    suspend fun getPopular(page: Int): RemoteMovies
    suspend fun getKategori(): RemoteCategory
    suspend fun getTopRatedTV(): RemoteTvs
    suspend fun getPopularTV(page: Int): RemoteTvs
    suspend fun getLatestTV(page: Int): RemoteTvs
    suspend fun getDetailTV(id: String): RemoteTv

    suspend fun getVideos(id:Int,type:String):RemoteVideos
    suspend fun getReviews(id:Int,type:String,page:Int):RemoteReviews
}