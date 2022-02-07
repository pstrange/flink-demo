package com.flink.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.flink.demo.model.data.response.FavMovie
import com.flink.demo.model.data.response.Movie
import com.flink.demo.model.repository.Repository

class FavMoviesViewModel(private val repository: Repository) : BaseViewModel() {

    val favMovies = MutableLiveData<List<Movie?>>()

    fun getMovies(){
        val favorites = repository.getFavorites().map { it.movie }
        favMovies.postValue(favorites)
    }

    private fun insertFavMovie(movie: Movie){
        val favMovie = FavMovie(movieId = movie.id, movie = movie)
        dispatchDb(object : Dispatcher(){
            override suspend fun execute() {
                repository.addFavorite(favMovie)
            }
        })
    }

    private fun deleteFavMovie(movie: Movie){
        val favMovie = FavMovie(movieId = movie.id, movie = movie)
        dispatchDb(object : Dispatcher(){
            override suspend fun execute() {
                repository.deleteFavorite(favMovie)
            }
        })
    }

}