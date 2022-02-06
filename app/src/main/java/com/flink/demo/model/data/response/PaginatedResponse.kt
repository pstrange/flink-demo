package com.flink.demo.model.data.response

data class PaginatedResponse<T>(
    var page: Int? = null,
    var results: List<T>? = null
)
