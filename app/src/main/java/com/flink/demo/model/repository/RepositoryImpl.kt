package com.flink.demo.model.repository

import com.flink.demo.model.data.request.CreateSession
import com.flink.demo.model.data.response.Authentication
import com.flink.demo.model.data.response.GuestSession
import com.flink.demo.model.remote.RemoteDataSource
import retrofit2.Response

class RepositoryImpl(
    private val service: RemoteDataSource) : Repository {

    override suspend fun authenticate(api_key: String): Response<Authentication> = service.authenticate(api_key)

    override suspend fun createSession(api_key: String): Response<GuestSession> = service.createSession(api_key)

}