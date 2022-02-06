package com.flink.demo.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flink.demo.model.data.response.FavMovie
import com.flink.demo.model.data.response.Movie
import com.flink.demo.model.data.response.RateMovie
import com.flink.demo.model.data.response.TopMovie

@Database(entities = [
    Movie::class,
    FavMovie::class,
    TopMovie::class,
    RateMovie::class],
    version = 1,
    exportSchema = false)
abstract class MoviesDB : RoomDatabase() {

    abstract val moviesDao: LocalDataSource

}