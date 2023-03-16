package com.example.favourite

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

class FavouriteViewModel
    (
) : ViewModel() {
    private lateinit var interactors: ProductInteractors
    var movieJob: Job? = null
    var tvJob: Job? = null
    var favouriteMovies: MutableLiveData<DataState<PagingData<Movie>>> = MutableLiveData()
    var favouriteTV: MutableLiveData<DataState<PagingData<TV>>> = MutableLiveData()


    fun setApi(interactors: ProductInteractors) {
        this.interactors = interactors
    }


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


}
