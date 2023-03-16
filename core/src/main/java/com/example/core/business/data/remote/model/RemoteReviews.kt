package com.example.core.business.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteReviews(


    val id: Int? = null,


    val page: Int? = null,


    val totalPages: Int? = null,


    val results: List<RemoteReview> ,


    val totalResults: Int? = null
)