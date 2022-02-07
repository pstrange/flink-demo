package com.flink.demo.model.remote

import com.flink.demo.model.data.response.Authentication
import com.flink.demo.model.data.response.GuestSession
import com.flink.demo.model.data.response.Movie
import com.flink.demo.model.data.response.PaginatedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteDataSource {

    @GET("authentication/token/new")
    suspend fun authenticate(
        @Query("api_key") api_key: String): Response<Authentication>

    @POST("authentication/guest_session/new")
    suspend fun createSession(
        @Query("api_key") api_key: String): Response<GuestSession>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int): Response<PaginatedResponse<Movie>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int): Response<PaginatedResponse<Movie>>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: String,
        @Query("api_key") api_key: String): Response<Movie>
}