package com.example.core.business.data.remote

import com.example.core.business.data.remote.model.*
import com.example.core.business.network.NetworkManagerService

class RemoteDataSourceImpl
constructor(
    private val networkManagerService: NetworkManagerService
) : RemoteDataSource {
    override suspend fun getTopMovies(page: Int): RemoteMovies {
        return networkManagerService.getTopMovies(page)
    }

    override suspend fun getDetailMovie(id: String): RemoteMovie {
        return networkManagerService.getDetailMovie(id)
    }
    override suspend fun getSimilar(filename: Int?): RemoteMovies {
        return networkManagerService.getSimilar(filename)
    }

    override suspend fun getSimilarTV(filename: Int?): RemoteTvs {
        return networkManagerService.getSimilarTV(filename)
    }

    override suspend fun getRecomendation(filename: Int?): RemoteMovies {
        return networkManagerService.getRecomendation(filename)
    }

    override suspend fun getRecomendationTV(filename: Int?): RemoteTvs {
        return networkManagerService.getRecomendationTV(filename)
    }

    override suspend fun getNowPlaying(page: Int): RemoteMovies {
        return networkManagerService.getNowPlaying(page)
    }

    override suspend fun getUpcoming(page: Int): RemoteMovies {
        return networkManagerService.getUpcoming(page)
    }

    override suspend fun getPopular(page: Int): RemoteMovies {
        return networkManagerService.getPopular(page)
    }

    override suspend fun getKategori(): RemoteCategory {
        return networkManagerService.getKategori()
    }

    override suspend fun getTopRatedTV(): RemoteTvs {
        return networkManagerService.getTopRatedTV()
    }

    override suspend fun getPopularTV(page: Int): RemoteTvs {
        return networkManagerService.getPopularTV(page)
    }

    override suspend fun getLatestTV(page: Int): RemoteTvs {
        return networkManagerService.getLatestTV(page)
    }

    override suspend fun getDetailTV(id: String): RemoteTv {
        return networkManagerService.getDetailTV(id)
    }

    override suspend fun getVideos(id: Int, type: String): RemoteVideos {
        return networkManagerService.getVideos(id,type)
    }

    override suspend fun getReviews(id: Int, type: String,page:Int): RemoteReviews {
        return networkManagerService.getReviews(id,type,page)
    }

}