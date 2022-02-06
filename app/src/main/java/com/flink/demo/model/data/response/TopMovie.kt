package com.flink.demo.model.data.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topMoviesTable")
data class TopMovie(
    @PrimaryKey(autoGenerate = false)
    var id: Long? = null,
    @Embedded
    var movie: Movie? = null
)
