package com.example.core.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.core.business.data.remote.RemoteDataSource
import com.example.core.business.domain.utils.AppConstant.PAGE_SIZE
import com.example.core.business.network.pagingsource.MoviePagingSource
import com.example.core.business.network.pagingsource.ReviewPagingSource
import com.example.core.business.network.pagingsource.TvPagingSource

object Helper {
    fun getMoviePager(service: RemoteDataSource, query: String) =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { MoviePagingSource(service = service, query = query) }
        ).flow

    fun getTVPager(service: RemoteDataSource, query: String) =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { TvPagingSource(service = service, query = query) }
        ).flow

    fun getReviewPager(service: RemoteDataSource, query: Int, type: String) =
        Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                ReviewPagingSource(
                    service = service,
                    query = query,
                    type = type
                )
            }
        ).flow
}