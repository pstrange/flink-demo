package com.flink.demo.model.data.response

data class Error(
    var status_message: String? = null,
    var status_code: Int? = null
)
