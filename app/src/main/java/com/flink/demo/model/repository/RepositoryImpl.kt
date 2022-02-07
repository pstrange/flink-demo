package com.flink.demo.model.repository

import com.flink.demo.model.data.response.*
import com.flink.demo.model.local.LocalDataSource
import com.flink.demo.model.remote.RemoteDataSource
import retrofit2.Response

class RepositoryImpl(
    private val local: LocalDataSource,
    private val service: RemoteDataSource) : Repository {

//    LOCAL

    override suspend fun addTopMovies(movies: List<TopMovie>) = local.addTopMovies(movies)

    override fun getTopMovies(): List<TopMovie> = local.getTopMovies()

    override suspend fun deleteAllTopMovies() = local.deleteAllTopMovies()

    override suspend fun addRateMovies(movies: List<RateMovie>) = local.addRateMovies(movies)

    override fun getRateMovies(): List<RateMovie> = local.getRateMovies()

    override suspend fun deleteAllRateMovies() = local.deleteAllRateMovies()

    override suspend fun addFavorite(movie: FavMovie) = local.addFavorite(movie)

    override fun getFavoritesIds(): List<Long> = local.getFavoritesIds()

    override fun getFavorites(): List<FavMovie> = local.getFavorites()

    override suspend fun deleteFavorite(movie: FavMovie) = local.deleteFavorite(movie)


//    REMOTE

    override suspend fun authenticate(api_key: String): Response<Authentication> = service.authenticate(api_key)

    override suspend fun createSession(api_key: String): Response<GuestSession> = service.createSession(api_key)

    override suspend fun getPopularMovies(api_key: String, page: Int): Response<PaginatedResponse<Movie>> = service.getPopularMovies(api_key, page)

    override suspend fun getTopRatedMovies(api_key: String, page: Int): Response<PaginatedResponse<Movie>> = service.getTopRatedMovies(api_key, page)

    override suspend fun getMovieDetail(movie_id: String, api_key: String): Response<Movie> = service.getMovieDetail(movie_id, api_key)

}