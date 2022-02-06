package com.flink.demo.model.data.response

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rateMoviesTable")
data class RateMovie(
    @PrimaryKey(autoGenerate = false)
    var id: Long? = null,
    @Embedded
    var movie: Movie? = null
)
