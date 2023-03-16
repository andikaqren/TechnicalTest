package com.example.technicaltest.home.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.andika.architecturecomponent.core.business.domain.model.TV
import com.andika.architecturecomponent.core.business.domain.model.TVs
import com.example.core.business.domain.state.DataState
import com.example.core.business.interactors.ProductInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvViewModel
@Inject constructor(
    private val interactors: ProductInteractors
) : ViewModel() {
    private var latestJob: Job? = null
    private var topJob: Job? = null
    private var popularJob: Job? = null
    private var _topTV: MutableLiveData<DataState<TVs>> = MutableLiveData()
    private var _popularTV: MutableLiveData<DataState<PagingData<TV>>> = MutableLiveData()
    private var _latestTV: MutableLiveData<DataState<PagingData<TV>>> = MutableLiveData()
    var topTV: LiveData<DataState<TVs>> = _topTV
    var popularTV: LiveData<DataState<PagingData<TV>>> = _popularTV
    var latestTV: LiveData<DataState<PagingData<TV>>> = _latestTV


    var dummyLoadingList: List<TV> = (listOf(
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

    var dummyErrorList: List<TV> = (listOf(
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

    fun reloadData() {
        latestJob?.cancel()
        popularJob?.cancel()
        topJob?.cancel()
        latestJob = viewModelScope.launch(Dispatchers.IO) {
            getLatestTV()
        }
        popularJob = viewModelScope.launch(Dispatchers.IO) {
            getPopularTV()
        }
        topJob = viewModelScope.launch(Dispatchers.IO) {
            getTopRatedTV()
        }
    }

    fun cancelJob(){
        latestJob?.cancel()
        popularJob?.cancel()
        topJob?.cancel()
    }


    suspend fun getTopRatedTV() {
        interactors.getTopRatedTv().collect {
            _topTV.postValue(it)
        }
    }

    suspend fun getPopularTV() {
        interactors.getPopularTv(viewModelScope).collect {
            _popularTV.postValue(it)
        }
    }

    suspend fun getLatestTV() {
        interactors.getLatestTV(viewModelScope).collect {
            _latestTV.postValue(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelJob()
    }
}