package com.example.core.business.data.remote.model

data class RemoteTvs(
    val dates: RemoteDates?,
    val page: Int,
    var results: List<RemoteTv>,
    val total_pages: Int,
    val total_results: Int
)