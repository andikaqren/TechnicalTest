package com.example.technicaltest.detail.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.business.data.remote.model.RemoteMovies
import com.example.core.business.data.remote.model.RemoteVideos
import com.example.core.business.domain.state.DataState
import com.example.core.business.interactors.ProductInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YoutubeViewModel
@Inject constructor(private val interactors: ProductInteractors): ViewModel() {

    private var _videos:MutableLiveData<DataState<RemoteVideos>> = MutableLiveData()
     var videos = _videos

    private var job: Job?=null

    fun reloadData(id:Int,type:String){
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO){
            getVideos(id,type)
        }
    }

    suspend fun getVideos(id:Int,type:String){
        interactors.getVideos(id,type).collect{
            _videos.postValue(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}