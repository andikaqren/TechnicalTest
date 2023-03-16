package com.example.core.business.network.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.example.core.business.data.remote.RemoteDataSource
import com.example.core.business.data.remote.model.RemoteMovies
import com.example.core.business.data.remote.model.RemoteReview
import com.example.core.business.domain.utils.AppConstant
import com.example.core.business.domain.utils.AppConstant.MOVIE
import com.example.core.business.domain.utils.AppConstant.TV
import com.example.core.business.domain.utils.DataMapper
import retrofit2.HttpException
import java.io.IOException

class ReviewPagingSource(
    private val service: RemoteDataSource,
    private val query: Int,
    private val type: String
) : PagingSource<Int, RemoteReview>() {
    private val MOVIE_STARTING_PAGE_INDEX = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RemoteReview> {
        val position = params.key ?: MOVIE_STARTING_PAGE_INDEX
        return try {
            val response = service.getReviews(query, type, position)
            val nextKey = if (response.results.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = response.results,
                prevKey = if (position == MOVIE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RemoteReview>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}