package com.flink.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.flink.demo.model.data.response.FavMovie
import com.flink.demo.model.data.response.Movie
import com.flink.demo.model.repository.Repository

class FavMoviesViewModel(private val repository: Repository) : BaseViewModel() {

    val favMovies = MutableLiveData<List<FavMovie>>()

    fun getMovies(){
        repository.getFavorites().value?.let { localMovies ->
            favMovies.postValue(localMovies)
        }
    }

    private fun insertFavMovie(movie: Movie){
        val favMovie = FavMovie(id = movie.id, movie = movie)
        dispatchDb(object : Dispatcher(){
            override suspend fun execute() {
                repository.addFavorite(favMovie)
            }
        })
    }

}