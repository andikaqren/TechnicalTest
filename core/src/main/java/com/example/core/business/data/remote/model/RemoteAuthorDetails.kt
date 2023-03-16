package com.example.core.business.data.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteAuthorDetails(

    @SerializedName("avatar_path")
    val avatarPath: String? = null,


    val name: String? = null,


    val rating: Double? = null,


    val username: String? = null
)