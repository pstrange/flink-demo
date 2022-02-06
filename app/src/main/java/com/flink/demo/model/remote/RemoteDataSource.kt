package com.flink.demo.model.remote

import com.flink.demo.model.data.request.CreateSession
import com.flink.demo.model.data.response.Authentication
import com.flink.demo.model.data.response.GuestSession
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RemoteDataSource {

    @GET("authentication/token/new")
    suspend fun authenticate(
        @Query("api_key") api_key: String): Response<Authentication>

    @POST("authentication/guest_session/new")
    suspend fun createSession(
        @Query("api_key") api_key: String): Response<GuestSession>

}