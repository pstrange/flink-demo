package com.flink.demo.model.data.response

data class Authentication(
    var success: Boolean? = null,
    var expires_at: String? = null,
    var request_token: String? = null,
)
