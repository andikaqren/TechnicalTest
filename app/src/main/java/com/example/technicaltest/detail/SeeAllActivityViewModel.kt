package com.example.technicaltest.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.example.core.business.domain.state.DataState
import com.example.core.business.domain.utils.AppConstant.LATEST_TV
import com.example.core.business.domain.utils.AppConstant.NOW_PLAYING_MOVIES
import com.example.core.business.domain.utils.AppConstant.POPULAR_MOVIES
import com.example.core.business.domain.utils.AppConstant.POPULAR_TV
import com.example.core.business.domain.utils.AppConstant.UPCOMING_MOVIES
import com.example.core.business.interactors.ProductInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeAllActivityViewModel
@Inject constructor(
    private val interactors: ProductInteractors
) : ViewModel() {


    private var _movie: MutableLiveData<DataState<PagingData<Movie>>> = MutableLiveData()
    private var _tv: MutableLiveData<DataState<PagingData<TV>>> = MutableLiveData()
     var movie: LiveData<DataState<PagingData<Movie>>> = _movie
     var tv: LiveData<DataState<PagingData<TV>>> = _tv



    private var seeAllJob: Job? = null


    fun reloadDataMovies(type: String) {
        seeAllJob?.cancel()
        seeAllJob = viewModelScope.launch(Dispatchers.IO) {
            getAllMovies(type)
        }
    }

    fun reloadDataTV(type: String) {
        seeAllJob?.cancel()
        seeAllJob = viewModelScope.launch(Dispatchers.IO) {
            getAllTv(type)
        }
    }


    suspend fun getAllMovies(type: String) {
        when(type){
            POPULAR_MOVIES->{
                interactors.getPopular(viewModelScope).collect {
                    _movie.postValue(it)
                }
            }
            NOW_PLAYING_MOVIES->{
                interactors.getNowPlaying(viewModelScope).collect {
                    _movie.postValue(it)
                }
            }
            UPCOMING_MOVIES->{
                interactors.getUpcoming(viewModelScope).collect {
                    _movie.postValue(it)
                }
            }

        }

    }
    suspend fun getAllTv(type: String) {
        when(type){
            POPULAR_TV->{
                interactors.getPopularTv(viewModelScope).collect {
                    _tv.postValue(it)
                }
            }
            LATEST_TV->{
                interactors.getLatestTV(viewModelScope).collect {
                    _tv.postValue(it)
                }
            }

        }

    }

    var dummyPagingData: PagingData<Movie> = PagingData.from(listOf(
        Movie(true, "Loading", listOf(1,2), 1,"Indonesia","Loading","Loading",10.0,"Loading","Loading","Loading",false,10.0,100),
        Movie(true, "Loading", listOf(1,2), 2,"Indonesia","Loading","Loading",10.0,"Loading","Loading","Loading",false,10.0,100),
        Movie(true, "Loading", listOf(1,2), 3,"Indonesia","Loading","Loading",10.0,"Loading","Loading","Loading",false,10.0,100),
        Movie(true, "Loading", listOf(1,2), 4,"Indonesia","Loading","Loading",10.0,"Loading","Loading","Loading",false,10.0,100),
        Movie(true, "Loading", listOf(1,2), 5,"Indonesia","Loading","Loading",10.0,"Loading","Loading","Loading",false,10.0,100)
    ))

    var dummyErrorPagingData: PagingData<Movie> = PagingData.from(listOf(
        Movie(true, "Error", listOf(1,2), 1,"Indonesia","Error","Error",10.0,"Error","Error","Error",false,10.0,100),
        Movie(true, "Error", listOf(1,2), 2,"Indonesia","Error","Error",10.0,"Error","Error","Error",false,10.0,100),
        Movie(true, "Error", listOf(1,2), 3,"Indonesia","Error","Error",10.0,"Error","Error","Error",false,10.0,100),
        Movie(true, "Error", listOf(1,2), 4,"Indonesia","Error","Error",10.0,"Error","Error","Error",false,10.0,100),
        Movie(true, "Error", listOf(1,2), 5,"Indonesia","Error","Error",10.0,"Error","Error","Error",false,10.0,100)
    ))

    var dummyLoadingPaging: PagingData<TV> = PagingData.from(listOf(
        TV(
            "Loading",
            listOf(1, 2),
            listOf("Loading"),
            1,
            "Loading",
            "Loading",
            "Loading",
            10.0,
            "Loading",
            "Loading",
            "Loading",
            false,
            10.0,
            10
        ),
        TV(
            "Loading",
            listOf(1, 2),
            listOf("Loading"),
            2,
            "Loading",
            "Loading",
            "Loading",
            10.0,
            "Loading",
            "Loading",
            "Loading",
            false,
            10.0,
            10
        ),
        TV(
            "Loading",
            listOf(1, 2),
            listOf("Loading"),
            3,
            "Loading",
            "Loading",
            "Loading",
            10.0,
            "Loading",
            "Loading",
            "Loading",
            false,
            10.0,
            10
        ),
        TV(
            "Loading",
            listOf(1, 2),
            listOf("Loading"),
            4,
            "Loading",
            "Loading",
            "Loading",
            10.0,
            "Loading",
            "Loading",
            "Loading",
            false,
            10.0,
            10
        ),
        TV(
            "Loading",
            listOf(1, 2),
            listOf("Loading"),
            5,
            "Loading",
            "Loading",
            "Loading",
            10.0,
            "Loading",
            "Loading",
            "Loading",
            false,
            10.0,
            10
        ),
    ))

    var dummyErrorPaging: PagingData<TV> = PagingData.from(listOf(
        TV(
            "Error",
            listOf(1, 2),
            listOf("Error"),
            1,
            "Error",
            "Error",
            "Error",
            10.0,
            "Error",
            "Error",
            "Error",
            false,
            10.0,
            10
        ),
        TV(
            "Error",
            listOf(1, 2),
            listOf("Error"),
            2,
            "Error",
            "Error",
            "Error",
            10.0,
            "Error",
            "Error",
            "Error",
            false,
            10.0,
            10
        ),
        TV(
            "Error",
            listOf(1, 2),
            listOf("Error"),
            3,
            "Error",
            "Error",
            "Error",
            10.0,
            "Error",
            "Error",
            "Error",
            false,
            10.0,
            10
        ),
        TV(
            "Error",
            listOf(1, 2),
            listOf("Error"),
            4,
            "Error",
            "Error",
            "Error",
            10.0,
            "Error",
            "Error",
            "Error",
            false,
            10.0,
            10
        ),
        TV(
            "Error",
            listOf(1, 2),
            listOf("Error"),
            5,
            "Error",
            "Error",
            "Error",
            10.0,
            "Error",
            "Error",
            "Error",
            false,
            10.0,
            10
        ),
    ))

    override fun onCleared() {
        super.onCleared()
        seeAllJob?.cancel()
    }
}