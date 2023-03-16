package com.example.technicaltest.detail.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.core.business.data.remote.model.RemoteReview
import com.example.core.business.domain.state.DataState
import com.example.core.business.interactors.ProductInteractors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel
@Inject constructor(private val interactors: ProductInteractors): ViewModel() {

    private var _reviews: MutableLiveData<DataState<PagingData<RemoteReview>>> = MutableLiveData()
    var reviews = _reviews

    private var job: Job?=null

    fun reloadData(id:Int,type:String){
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO){
            getReview(id,type)
        }
    }

    suspend fun getReview(id: Int,type:String) {
        interactors.getReviews(viewModelScope,id,type).collect {
            _reviews.postValue(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}