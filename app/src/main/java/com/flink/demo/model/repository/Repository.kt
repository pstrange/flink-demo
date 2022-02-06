package com.flink.demo.model.repository

import com.flink.demo.model.data.response.Authentication
import com.flink.demo.model.data.response.GuestSession
import retrofit2.Response

interface Repository {

    suspend fun authenticate(api_key: String) : Response<Authentication>

    suspend fun createSession(api_key: String) : Response<GuestSession>

}