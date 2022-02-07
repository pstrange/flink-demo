package com.flink.demo.model.data.request

import com.flink.demo.model.data.response.Movie

class Bookmark(
    val index: Int = -1,
    val subIndex: Int? = null,
    var movie: Movie?
)