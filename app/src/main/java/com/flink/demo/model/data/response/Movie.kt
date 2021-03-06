package com.flink.demo.model.data.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "moviesTable")
data class Movie(
    @PrimaryKey(autoGenerate = false)
    var id: Long? = null,
    @ColumnInfo(name = "posterPath")
    var poster_path: String? = null,
    @ColumnInfo(name = "overview")
    var overview: String? = null,
    @ColumnInfo(name = "releaseDate")
    var release_date: String? = null,
    @ColumnInfo(name = "originalTitle")
    var original_title: String? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "backdropPath")
    var backdrop_path: String? = null,
    @ColumnInfo(name = "popularity")
    var popularity: Double? = null,
    @ColumnInfo(name = "voteCount")
    var vote_count: String? = null,
    @Ignore
    var production_companies: List<Company>? = null,
    @Ignore
    var genres: List<Locale>? = null,
    @Ignore
    var homepage: String? = null,
    @Ignore
    var isBookmark: Boolean = false,
) : Serializable
