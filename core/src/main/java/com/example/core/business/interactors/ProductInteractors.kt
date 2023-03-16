package com.example.core.business.interactors

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.Movies
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.model.TVs
import com.example.core.business.data.local.LocalDataSource
import com.example.core.business.data.remote.RemoteDataSource
import com.example.core.business.data.remote.model.RemoteReview
import com.example.core.business.data.remote.model.RemoteReviews
import com.example.core.business.data.remote.model.RemoteVideos
import com.example.core.business.domain.state.DataState
import com.example.core.business.domain.utils.AppConstant.LATEST_TV
import com.example.core.business.domain.utils.AppConstant.NOW_PLAYING_MOVIES
import com.example.core.business.domain.utils.AppConstant.POPULAR_MOVIES
import com.example.core.business.domain.utils.AppConstant.POPULAR_TV
import com.example.core.business.domain.utils.AppConstant.UPCOMING_MOVIES
import com.example.core.business.domain.utils.DataMapper
import com.example.core.di.Helper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductInteractors
constructor(
    private val localDataSource: LocalDataSource,
    private val networkDataSource: RemoteDataSource
) {

    suspend fun insertSelectedTV(tv: TV) {
        localDataSource.insertSelectedTV(DataMapper.tvToLocalTV(tv))
    }

    suspend fun insertSelectedMovie(movie: Movie) {
        localDataSource.insertSelectedMovie(DataMapper.movieToLocalMovie(movie))
    }

    suspend fun removeSelectedMovie(movie: Movie) {
        localDataSource.removeSelectedMovie(DataMapper.movieToLocalMovie(movie))
    }

    suspend fun removeSelectedTV(tv: TV) {
        localDataSource.removeSelectedTV(DataMapper.tvToLocalTV(tv))
    }

    fun getAllFavouriteMovie(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>> =
        flow {
            emit(DataState.Loading)

            try {
                localDataSource.getAllFavMovies().cachedIn(scope).collect {
                    val data = it
                    emit(DataState.Success(data.map { DataMapper.localMovieToMovie(it) }))
                }
            } catch (e: Exception) {
                emit(DataState.Error(e))

            }
        }

    fun getAllFavouriteTV(scope: CoroutineScope): Flow<DataState<PagingData<TV>>> = flow {
        emit(DataState.Loading)

        try {
            localDataSource.getAllFavTV().cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data.map { DataMapper.localTVToTV(it) }))

            }
        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }

    fun getSelectedMovie(id: Int): Flow<DataState<Movie>> = flow {
        emit(DataState.Loading)

        try {
            val data = DataMapper.localMovieToMovie(localDataSource.getSelectedMovie(id))
            emit(DataState.Success(data))

        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }


    fun getSelectedTV(id: Int): Flow<DataState<TV>> = flow {
        emit(DataState.Loading)

        try {
            val data = DataMapper.localTVToTV(localDataSource.getSelectedTV(id))
            emit(DataState.Success(data))

        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }

    fun getDetailTV(id: String): Flow<DataState<TV>> = flow {
        emit(DataState.Loading)

        try {
            val data = DataMapper.remoteTVToTV(networkDataSource.getDetailTV(id))
            emit(DataState.Success(data))

        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }

    fun getDetailMovie(id: String): Flow<DataState<Movie>> = flow {
        emit(DataState.Loading)

        try {
            val data = DataMapper.remoteMovieToMovie(networkDataSource.getDetailMovie(id))
            emit(DataState.Success(data))

        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }


    fun getSimilar(filename: Int): Flow<DataState<Movies>> = flow {

        emit(DataState.Loading)
        try {
            val data =
                DataMapper.remoteMoviesToMovies(networkDataSource.getSimilar(filename = filename))
            emit(DataState.Success(data))

        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }

    fun getVideos(id: Int, type: String): Flow<DataState<RemoteVideos>> =
        flow {
            emit(DataState.Loading)

            try {
                val data = networkDataSource.getVideos(id, type)
                emit(DataState.Success(data))


            } catch (e: Exception) {
                emit(DataState.Error(e))

            }
        }

    fun getSimilarTV(filename: Int): Flow<DataState<TVs>> = flow {
        emit(DataState.Loading)

        try {
            val data =
                DataMapper.remoteTVsToTVs(networkDataSource.getSimilarTV(filename = filename))
            emit(DataState.Success(data))

        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }

    fun getRecomendation(filename: Int): Flow<DataState<Movies>> = flow {
        emit(DataState.Loading)

        try {
            val data =
                DataMapper.remoteMoviesToMovies(networkDataSource.getRecomendation(filename = filename))
            emit(DataState.Success(data))

        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }

    fun getRecomendationTV(filename: Int): Flow<DataState<TVs>> = flow {
        emit(DataState.Loading)

        try {
            val data =
                DataMapper.remoteTVsToTVs(networkDataSource.getRecomendationTV(filename = filename))
            emit(DataState.Success(data))

        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }

    fun getTopMovies(): Flow<DataState<Movies>> = flow {
        emit(DataState.Loading)

        try {
            val data = DataMapper.remoteMoviesToMovies(networkDataSource.getTopMovies(1))
            emit(DataState.Success(data))


        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }


    fun getUpcoming(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>> = flow {
        emit(DataState.Loading)

        try {


            Helper.getMoviePager(networkDataSource, UPCOMING_MOVIES).cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))

            }


        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }

    fun getNowPlaying(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>> = flow {
        emit(DataState.Loading)

        try {
            Helper.getMoviePager(networkDataSource, NOW_PLAYING_MOVIES).cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))

            }

        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }


    fun getPopular(scope: CoroutineScope): Flow<DataState<PagingData<Movie>>> = flow {
        emit(DataState.Loading)

        try {
            Helper.getMoviePager(networkDataSource, POPULAR_MOVIES).cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))

            }

        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }


    fun getPopularTv(scope: CoroutineScope): Flow<DataState<PagingData<TV>>> = flow {
        emit(DataState.Loading)

        try {
            Helper.getTVPager(networkDataSource, POPULAR_TV).cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))

            }
        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }

    fun getReviews(
        scope: CoroutineScope,
        query: Int,
        type: String
    ): Flow<DataState<PagingData<RemoteReview>>> = flow {
        emit(DataState.Loading)

        try {
            Helper.getReviewPager(networkDataSource, query, type).cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))

            }
        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }

    fun getTopRatedTv(): Flow<DataState<TVs>> = flow {
        emit(DataState.Loading)

        try {
            val data = DataMapper.remoteTVsToTVs(networkDataSource.getTopRatedTV())
            emit(DataState.Success(data))

        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }

    fun getLatestTV(scope: CoroutineScope): Flow<DataState<PagingData<TV>>> = flow {
        emit(DataState.Loading)

        try {
            Helper.getTVPager(networkDataSource, LATEST_TV).cachedIn(scope).collect {
                val data = it
                emit(DataState.Success(data))

            }
        } catch (e: Exception) {
            emit(DataState.Error(e))

        }
    }


}