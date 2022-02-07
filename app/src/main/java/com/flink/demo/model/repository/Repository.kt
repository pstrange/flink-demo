package com.flink.demo.model.repository

import androidx.lifecycle.LiveData
import com.flink.demo.model.data.response.*
import retrofit2.Response

interface Repository {

//    LOCAL

    suspend fun addTopMovies(movies: List<TopMovie>)

    fun getTopMovies() : LiveData<List<TopMovie>>

    suspend fun deleteAllTopMovies()

    suspend fun addRateMovies(movies: List<RateMovie>)

    fun getRateMovies() : LiveData<List<RateMovie>>

    suspend fun deleteAllRateMovies()

    suspend fun addFavorite(movie: FavMovie)

    fun getFavoritesIds(): List<Long>

    fun getFavorites(): LiveData<List<FavMovie>>

    suspend fun deleteFavorite(movie: FavMovie)

//    REMOTE

    suspend fun authenticate(api_key: String) : Response<Authentication>

    suspend fun createSession(api_key: String) : Response<GuestSession>

    suspend fun getPopularMovies(api_key: String, page: Int) : Response<PaginatedResponse<Movie>>

    suspend fun getTopRatedMovies(api_key: String, page: Int) : Response<PaginatedResponse<Movie>>

    suspend fun getMovieDetail(movie_id: String, api_key: String) : Response<Movie>

}