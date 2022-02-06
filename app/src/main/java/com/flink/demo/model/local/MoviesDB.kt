package com.flink.demo.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flink.demo.model.data.response.FavMovie
import com.flink.demo.model.data.response.Movie

@Database(entities = [
    Movie::class,
    FavMovie::class],
    version = 1,
    exportSchema = false)
abstract class MoviesDB : RoomDatabase() {

    abstract val moviesDao: LocalDataSource

}