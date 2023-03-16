package com.example.core.business.network

import com.example.core.business.data.remote.model.*
import com.example.core.business.domain.utils.AppConstant.MOVIE
import com.example.core.business.domain.utils.AppConstant.TV

class NetworkManagerServiceImpl
constructor(
    private val networkManager: NetworkManager
) : NetworkManagerService {
    override suspend fun getTopMovies(page: Int): RemoteMovies {
        return networkManager.getTopMovies(page)
    }



    override suspend fun getSimilar(filename: Int?): RemoteMovies {
        return networkManager.getSimilar(filename)
    }

    override suspend fun getSimilarTV(filename: Int?): RemoteTvs {
        return networkManager.getSimilarTV(filename)
    }

    override suspend fun getRecomendation(filename: Int?): RemoteMovies {
        return networkManager.getRecomendation(filename)
    }

    override suspend fun getRecomendationTV(filename: Int?): RemoteTvs {
        return networkManager.getRecomendationTV(filename)
    }

    override suspend fun getNowPlaying(page: Int): RemoteMovies {
        return networkManager.getNowPlaying(page)
    }

    override suspend fun getUpcoming(page: Int): RemoteMovies {
        return networkManager.getUpcoming(page)
    }

    override suspend fun getPopular(page: Int): RemoteMovies {
        return networkManager.getPopular(page)
    }

    override suspend fun getKategori(): RemoteCategory {
        return networkManager.getKategori()
    }

    override suspend fun getTopRatedTV(): RemoteTvs {
        return networkManager.getTopRatedTv()
    }

    override suspend fun getPopularTV(page: Int): RemoteTvs {
        return networkManager.getPopularTv(page)
    }

    override suspend fun getLatestTV(page: Int): RemoteTvs {
        return networkManager.getLatestTv(page)
    }

    override suspend fun getDetailTV(id: String): RemoteTv {
        return networkManager.getDetailTV(id.toInt())
    }

    override suspend fun getDetailMovie(id: String): RemoteMovie {
        return networkManager.getDetailMovie(id.toInt())
    }

    override suspend fun getVideos(id: Int, type: String): RemoteVideos {
        return when(type){
            MOVIE-> networkManager.getVideo(id)
            TV->networkManager.getVideoTv(id)
            else->networkManager.getVideo(id)
        }
    }

    override suspend fun getReviews(id: Int, type: String,page:Int): RemoteReviews {
        return when(type){
            MOVIE-> networkManager.getReviews(id,page)
            TV->networkManager.getReviewsTv(id,page)
            else->networkManager.getReviews(id,page)
        }
    }

}