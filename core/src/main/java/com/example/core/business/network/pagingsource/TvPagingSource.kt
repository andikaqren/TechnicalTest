package com.example.core.business.network.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.example.core.business.data.remote.RemoteDataSource
import com.example.core.business.data.remote.model.RemoteTvs
import com.example.core.business.domain.utils.AppConstant.LATEST_TV
import com.example.core.business.domain.utils.AppConstant.POPULAR_TV
import com.example.core.business.domain.utils.DataMapper
import retrofit2.HttpException
import java.io.IOException

class TvPagingSource(
    private val service: RemoteDataSource,
    private val query: String
) : PagingSource<Int, TV>() {
    private val TV_STARTING_PAGE_INDEX = 1
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TV> {
        val position = params.key ?: TV_STARTING_PAGE_INDEX
        return try {
            val response: RemoteTvs = when (query) {
                POPULAR_TV -> service.getPopularTV(position)
                LATEST_TV -> service.getLatestTV(position)
                else -> service.getPopularTV(position)
            }
            val nextKey = if (response.results.isEmpty()) {
                null
            } else {
                position + 1
            }
            LoadResult.Page(
                data = DataMapper.listRemoteTVToTV(response.results),
                prevKey = if (position == TV_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TV>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}