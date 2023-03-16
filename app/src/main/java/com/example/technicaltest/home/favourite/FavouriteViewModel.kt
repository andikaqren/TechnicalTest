package com.example.technicaltest.home.favourite


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData

import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.example.core.business.domain.state.DataState

import com.example.core.business.interactors.ProductInteractors
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouriteViewModel @Inject constructor(private val interactors: ProductInteractors) : ViewModel() {

    var movieJob: Job? = null
    var tvJob: Job? = null
    var favouriteMovies: MutableLiveData<DataState<PagingData<Movie>>> = MutableLiveData()
    var favouriteTV: MutableLiveData<DataState<PagingData<TV>>> = MutableLiveData()



    fun reloadData() {
        movieJob?.cancel()
        tvJob?.cancel()
        movieJob = viewModelScope.launch(Dispatchers.IO) {
            getFavMovie()
        }
        tvJob = viewModelScope.launch(Dispatchers.IO) {
            getFavTV()
        }

    }

    fun cancelJob(){
        movieJob?.cancel()
        tvJob?.cancel()
    }

    suspend fun getFavTV() {
        interactors.getAllFavouriteTV(viewModelScope).collect {
            favouriteTV.postValue(it)
        }
    }

    suspend fun getFavMovie() {
        interactors.getAllFavouriteMovie(viewModelScope).collect {
            favouriteMovies.postValue(it)
        }
    }
    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }


}
