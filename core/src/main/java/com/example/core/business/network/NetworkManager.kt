package com.example.core.business.network

import com.example.core.business.data.remote.model.*
import com.example.core.business.domain.utils.AppConstant
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkManager {
    @GET("movie/top_rated?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getTopMovies(@Query("page") page: Int): RemoteMovies

    @GET("movie/{fileName}/reviews?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getReviews(@Path("fileName") filename: Int?,@Query("page") page: Int): RemoteReviews

    @GET("tv/{fileName}/reviews?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getReviewsTv(@Path("fileName") filename: Int?,@Query("page") page: Int): RemoteReviews
    @GET("movie/{fileName}/videos?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getVideo(@Path("fileName") filename: Int?): RemoteVideos
    @GET("tv/{fileName}/videos?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getVideoTv(@Path("fileName") filename: Int?): RemoteVideos

    @GET("movie/{fileName}/similar?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getSimilar(@Path("fileName") filename: Int?): RemoteMovies

    @GET("movie/{fileName}/recommendations?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getRecomendation(@Path("fileName") filename: Int?): RemoteMovies

    @GET("tv/{fileName}/similar?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getSimilarTV(@Path("fileName") filename: Int?): RemoteTvs

    @GET("tv/{fileName}/recommendations?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getRecomendationTV(@Path("fileName") filename: Int?): RemoteTvs

    @GET("movie/now_playing?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getNowPlaying(@Query("page") page: Int): RemoteMovies

    @GET("movie/upcoming?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getUpcoming(@Query("page") page: Int): RemoteMovies

    @GET("movie/popular?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getPopular(@Query("page") page: Int): RemoteMovies

    @GET("tv/popular?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getPopularTv(@Query("page") page: Int): RemoteTvs

    @GET("tv/on_the_air?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getLatestTv(@Query("page") page: Int): RemoteTvs

    @GET("tv/top_rated?api_key=${AppConstant.movieDBKEY}&language=en-US&page=1")
    suspend fun getTopRatedTv(): RemoteTvs

    @GET("genre/movie/list?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getKategori(): RemoteCategory

    @GET("tv/{tv_id}?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getDetailTV(@Path("tv_id") fileId: Int): RemoteTv

    @GET("movie/{movie_id}?api_key=${AppConstant.movieDBKEY}&language=en-US")
    suspend fun getDetailMovie(@Path("movie_id") fileId: Int): RemoteMovie
}