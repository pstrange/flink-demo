package com.flink.demo.model.data.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritesTable")
data class FavMovie(
    @PrimaryKey(autoGenerate = false)
    var movieId: Long? = null,
    @Embedded
    var movie: Movie? = null
)
