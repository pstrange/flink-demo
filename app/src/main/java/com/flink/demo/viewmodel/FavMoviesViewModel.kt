package com.flink.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.flink.demo.model.data.request.CoverElement
import com.flink.demo.model.data.response.FavMovie
import com.flink.demo.model.data.response.Movie
import com.flink.demo.model.repository.Repository
import com.flink.demo.viewmodel.preferences.AppPreferences

class FavMoviesViewModel(private val repository: Repository) : BaseViewModel() {

    val favMovies = MutableLiveData<List<Movie?>>()

    private fun getFavMoviesIds(){
        val ids = repository.getFavoritesIds()
        AppPreferences.BOOKMAKS = ids
    }

    fun getMovies(){
        dispatchDb(object : Dispatcher(){
            override suspend fun execute() {
                val favorites = repository.getFavorites().map { it.movie }
                favMovies.postValue(favorites)
            }
        })
    }

    fun deleteFavMovies(movie: Movie){
        dispatchDb(object : Dispatcher(){
            override suspend fun execute() {
                repository.deleteFavorite(FavMovie(movieId = movie.id, movie = movie))
                getFavMoviesIds()
                getMovies()
            }
        })
    }

    fun parseListElements(
        elements: List<Movie?>,
        items : ArrayList<CoverElement>){

        if(!elements.isNullOrEmpty()){
            elements.forEach { movie ->
                items.add(
                    CoverElement(
                        type = CoverElement.Type.ITEM_POST,
                        data = movie)
                )
            }
        }
    }

}