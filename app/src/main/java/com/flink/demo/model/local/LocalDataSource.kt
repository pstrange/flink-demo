package com.flink.demo.model.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.flink.demo.model.data.response.FavMovie
import com.flink.demo.model.data.response.Movie

@Dao
interface LocalDataSource {

//    MOVIES

    @Insert(onConflict = REPLACE)
    fun addMovie(movie: Movie)

    @Insert(onConflict = REPLACE)
    fun addMovies(movies: List<Movie>)

    @Query("select * from moviesTable")
    fun getMovies(): LiveData<List<Movie>>

    @Query("DELETE FROM moviesTable")
    fun deleteAllMovies()

//    FAVORITES

    @Insert(onConflict = REPLACE)
    fun addFavorite(movie: FavMovie)

    @Query("select id from favoritesTable")
    fun getFavoritesIds(): LiveData<List<Long>>

    @Query("select * from favoritesTable")
    fun getFavorites(): LiveData<List<FavMovie>>

    @Delete
    fun deleteFavorite(movie: FavMovie)
}