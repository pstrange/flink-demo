package com.flink.demo.model.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.flink.demo.model.data.response.FavMovie
import com.flink.demo.model.data.response.Movie
import com.flink.demo.model.data.response.RateMovie
import com.flink.demo.model.data.response.TopMovie

@Dao
interface LocalDataSource {

//  RATE MOVIES

    @Insert(onConflict = REPLACE)
    fun addRateMovie(movie: RateMovie)

    @Insert(onConflict = REPLACE)
    fun addRateMovies(movies: List<RateMovie>)

    @Query("select * from rateMoviesTable")
    fun getRateMovies(): List<RateMovie>

    @Query("DELETE FROM rateMoviesTable")
    fun deleteAllRateMovies()

//  TOP MOVIES

    @Insert(onConflict = REPLACE)
    fun addTopMovie(movie: TopMovie)

    @Insert(onConflict = REPLACE)
    fun addTopMovies(movies: List<TopMovie>)

    @Query("select * from topMoviesTable")
    fun getTopMovies(): List<TopMovie>

    @Query("DELETE FROM topMoviesTable")
    fun deleteAllTopMovies()

//  FAVORITES

    @Insert(onConflict = REPLACE)
    fun addFavorite(movie: FavMovie)

    @Query("select id from favoritesTable")
    fun getFavoritesIds(): List<Long>

    @Query("select * from favoritesTable")
    fun getFavorites(): List<FavMovie>

    @Delete
    fun deleteFavorite(movie: FavMovie)

}