package com.example.core.business.data.remote.model

import com.google.gson.annotations.SerializedName


data class RemoteReview(

    @SerializedName("author_details")
    val authorDetails: RemoteAuthorDetails? = null,


    val updatedAt: String? = null,


    val author: String? = null,


    val createdAt: String? = null,


    val id: String? = null,


    val content: String? = null,


    val url: String? = null
)