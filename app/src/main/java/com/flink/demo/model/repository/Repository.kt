package com.flink.demo.model.repository

import androidx.lifecycle.LiveData
import com.flink.demo.model.data.response.*
import retrofit2.Response

interface Repository {

//    LOCAL

    fun addMovie(movie: Movie)

    suspend fun addMovies(movies: List<Movie>)

    fun getMovies() : LiveData<List<Movie>>

    suspend fun deleteAllMovies()

    suspend fun addFavorite(movie: FavMovie)

    fun getFavoritesIds(): LiveData<List<Long>>

    fun getFavorites(): LiveData<List<FavMovie>>

    suspend fun deleteFavorite(movie: FavMovie)

//    REMOTE

    suspend fun authenticate(api_key: String) : Response<Authentication>

    suspend fun createSession(api_key: String) : Response<GuestSession>

    suspend fun getPopularMovies(api_key: String, page: Int) : Response<PaginatedResponse<Movie>>

    suspend fun getTopRatedMovies(api_key: String, page: Int) : Response<PaginatedResponse<Movie>>

    suspend fun getMovieDetail(movie_id: String, api_key: String) : Response<Movie>

}