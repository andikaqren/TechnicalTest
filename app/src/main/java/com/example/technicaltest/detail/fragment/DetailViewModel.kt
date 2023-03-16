package com.example.technicaltest.detail.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andika.architecturecomponent.core.business.domain.model.Movie
import com.andika.architecturecomponent.core.business.domain.model.Movies
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.model.TVs
import com.example.core.business.domain.state.DataState
import com.example.core.business.domain.utils.AppConstant
import com.example.core.business.interactors.ProductInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor(
    private val interactors: ProductInteractors
) : ViewModel() {
    private var _similarMovies: MutableLiveData<DataState<Movies>> = MutableLiveData()
    private var _recomendationMovies: MutableLiveData<DataState<Movies>> = MutableLiveData()
    private var _similarTV: MutableLiveData<DataState<TVs>> = MutableLiveData()
    private var _recomendationTV: MutableLiveData<DataState<TVs>> = MutableLiveData()
    private var _tv: MutableLiveData<DataState<TV>> = MutableLiveData()
    private var _movie: MutableLiveData<DataState<Movie>> = MutableLiveData()
    private var _favMovie: MutableLiveData<DataState<Movie>> = MutableLiveData()
    private var _favTV: MutableLiveData<DataState<TV>> = MutableLiveData()

    var similarMovies: LiveData<DataState<Movies>> = _similarMovies
    var recomendationMovies: LiveData<DataState<Movies>> = _recomendationMovies
    var similarTV: LiveData<DataState<TVs>> = _similarTV
    var recomendationTV: LiveData<DataState<TVs>> = _recomendationTV
    var tv: LiveData<DataState<TV>> = _tv
    var movie: LiveData<DataState<Movie>> = _movie

    private var similarJob: Job? = null
    private var recomendationJob: Job? = null
    private var favJob: Job? = null


    fun reloadData(filename: Int) {
        cancelReloadJob()
        favJob = viewModelScope.launch(Dispatchers.IO) {
            getSelectedMovie(filename)
        }
        similarJob = viewModelScope.launch(Dispatchers.IO) {
            getSimilarMovie(filename)
        }
        recomendationJob = viewModelScope.launch(Dispatchers.IO) {
            getRecomendationMovie(filename)
        }
    }

    fun reloadDataTV(filename: Int) {
        cancelReloadJob()

        favJob = viewModelScope.launch(Dispatchers.IO) {
            getSelectedTV(filename)
        }
        similarJob = viewModelScope.launch(Dispatchers.IO) {
            getSimilarTV(filename)
        }
        recomendationJob = viewModelScope.launch(Dispatchers.IO) {
            getRecomendationTV(filename)
        }
    }

    fun cancelReloadJob() {
        favJob?.cancel()
        recomendationJob?.cancel()
        similarJob?.cancel()
    }

    suspend fun getRecomendationTV(filename: Int) {
        interactors.getRecomendationTV(filename).collect {
            _recomendationTV.postValue(it)
        }
    }

    suspend fun getSimilarTV(filename: Int) {
        interactors.getSimilarTV(filename).collect {
            _similarTV.postValue(it)
        }
    }


    suspend fun getRecomendationMovie(filename: Int) {
        interactors.getRecomendation(filename).collect {
            _recomendationMovies.postValue(it)
        }
    }

    suspend fun getSimilarMovie(filename: Int) {
        interactors.getSimilar(filename).collect {
            _similarMovies.postValue(it)
        }
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

    override fun onCleared() {
        super.onCleared()
        cancelReloadJob()
    }
}