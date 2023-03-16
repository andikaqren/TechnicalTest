package com.example.technicaltest.home.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.Movies
import com.example.core.business.domain.state.DataState
import com.example.core.business.interactors.ProductInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MoviesViewModel @Inject constructor(private val interactors: ProductInteractors) : ViewModel() {

    private var nowPlayingJob: Job? = null
    private var popularJob: Job? = null
    private var upcomingJob: Job? = null
    private var topRatedJob: Job? = null
    private var _topMovies: MutableLiveData<DataState<Movies>> = MutableLiveData()
    private var _nowPlayingMovies: MutableLiveData<DataState<PagingData<Movie>>> = MutableLiveData()
    private var _popularMovies: MutableLiveData<DataState<PagingData<Movie>>> = MutableLiveData()
    var _upcomingMovies: MutableLiveData<DataState<PagingData<Movie>>> = MutableLiveData()
    var topMovies: LiveData<DataState<Movies>> = _topMovies
    var popularMovies: LiveData<DataState<PagingData<Movie>>> = _popularMovies
    var nowPlayingMovies: LiveData<DataState<PagingData<Movie>>> = _nowPlayingMovies
    var upcomingMovies: LiveData<DataState<PagingData<Movie>>> = _upcomingMovies

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

    var dummyLoadingList: List<Movie> = (listOf(
        Movie(true, "Loading", listOf(1,2), 1,"Indonesia","Loading","Loading",10.0,"Loading","Loading","Loading",false,10.0,100),
        Movie(true, "Loading", listOf(1,2), 2,"Indonesia","Loading","Loading",10.0,"Loading","Loading","Loading",false,10.0,100),
        Movie(true, "Loading", listOf(1,2), 3,"Indonesia","Loading","Loading",10.0,"Loading","Loading","Loading",false,10.0,100),
        Movie(true, "Loading", listOf(1,2), 4,"Indonesia","Loading","Loading",10.0,"Loading","Loading","Loading",false,10.0,100),
        Movie(true, "Loading", listOf(1,2), 5,"Indonesia","Loading","Loading",10.0,"Loading","Loading","Loading",false,10.0,100)
    ))

    var dummyErrorList: List<Movie> = (listOf(
        Movie(true, "Error", listOf(1,2), 1,"Indonesia","Error","Error",10.0,"Error","Error","Error",false,10.0,100),
        Movie(true, "Error", listOf(1,2), 2,"Indonesia","Error","Error",10.0,"Error","Error","Error",false,10.0,100),
        Movie(true, "Error", listOf(1,2), 3,"Indonesia","Error","Error",10.0,"Error","Error","Error",false,10.0,100),
        Movie(true, "Error", listOf(1,2), 4,"Indonesia","Error","Error",10.0,"Error","Error","Error",false,10.0,100),
        Movie(true, "Error", listOf(1,2), 5,"Indonesia","Error","Error",10.0,"Error","Error","Error",false,10.0,100)
    ))

    fun reloadData() {
        nowPlayingJob?.cancel()
        popularJob?.cancel()
        upcomingJob?.cancel()
        topRatedJob?.cancel()
        nowPlayingJob = viewModelScope.launch(Dispatchers.IO) {
            getNowPlayingMovies()
        }
        upcomingJob = viewModelScope.launch(Dispatchers.IO) {
            getUpcomingMovies()
        }
        popularJob = viewModelScope.launch(Dispatchers.IO) {
            getPopularMovies()
        }
        topRatedJob = viewModelScope.launch(Dispatchers.IO) {
            getTopMovies()
        }


    }

    fun cancelJob(){
        nowPlayingJob?.cancel()
        popularJob?.cancel()
        upcomingJob?.cancel()
        topRatedJob?.cancel()
    }



    suspend fun getTopMovies() {
        interactors.getTopMovies().collect {
            _topMovies.postValue(it)
        }
    }

    suspend fun getPopularMovies() {
        interactors.getPopular(viewModelScope).collect {
            _popularMovies.postValue(it)
        }
    }

    suspend fun getNowPlayingMovies() {
        interactors.getNowPlaying(viewModelScope).collect {
            _nowPlayingMovies.postValue(it)
        }
    }

    suspend fun getUpcomingMovies() {
        interactors.getUpcoming(viewModelScope).collect {
            _upcomingMovies.postValue(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}