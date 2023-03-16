package com.example.technicaltest.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.Movies
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.model.TVs
import com.example.core.business.domain.state.DataState
import com.example.core.business.domain.utils.AppConstant.MOVIE
import com.example.core.business.domain.utils.AppConstant.TV
import com.example.core.business.interactors.ProductInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailActivityViewModel
@Inject constructor(
    private val interactors: ProductInteractors
) : ViewModel() {
    private var _tv: MutableLiveData<DataState<TV>> = MutableLiveData()
    private var _movie: MutableLiveData<DataState<Movie>> = MutableLiveData()
    private var _favMovie: MutableLiveData<DataState<Movie>> = MutableLiveData()
    private var _favTV: MutableLiveData<DataState<TV>> = MutableLiveData()


    var tv: LiveData<DataState<TV>> = _tv
    var movie: LiveData<DataState<Movie>> = _movie
    var favMovie: LiveData<DataState<Movie>> = _favMovie
    var favTV: LiveData<DataState<TV>> = _favTV


    private var favJob: Job? = null


    fun reloadData(filename: Int) {
        cancelReloadJob()
        favJob = viewModelScope.launch(Dispatchers.IO) {
            getSelectedMovie(filename)
        }
    }

    fun reloadDataTV(filename: Int) {
        cancelReloadJob()
        favJob = viewModelScope.launch(Dispatchers.IO) {
            getSelectedTV(filename)
        }
    }

    fun cancelReloadJob() {
        favJob?.cancel()

    }


    suspend fun getSelectedTV(filename: Int) {
        interactors.getSelectedTV(filename).collect {
            _favTV.postValue(it)
        }
    }
    suspend fun getSelectedMovie(filename: Int) {
        interactors.getSelectedMovie(filename).collect {
            _favMovie.postValue(it)
        }
    }

    fun setFav(isFavourite: Boolean, type: String, data: Any) {
        viewModelScope.launch(Dispatchers.IO) {
            when (type) {
                MOVIE -> {
                    selectFavMovie(isFavourite, data as Movie)
                }
                TV -> {
                    selectFavTV(isFavourite, data as TV)
                }
            }
        }
    }

    suspend fun selectFavTV(
        isFavourite: Boolean,
        localTV: TV
    ) {
        if (isFavourite) interactors.insertSelectedTV(localTV)
        else interactors.removeSelectedTV(localTV)
    }

    suspend fun selectFavMovie(
        isFavourite: Boolean,
        localMovie: Movie
    ) {
        if (isFavourite) interactors.insertSelectedMovie(localMovie)
        else interactors.removeSelectedMovie(localMovie)
    }

    override fun onCleared() {
        super.onCleared()
        cancelReloadJob()
    }
}