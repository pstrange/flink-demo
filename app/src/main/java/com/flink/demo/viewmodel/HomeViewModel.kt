package com.flink.demo.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.flink.demo.BuildConfig
import com.flink.demo.model.data.response.Movie
import com.flink.demo.model.data.response.PaginatedResponse
import com.flink.demo.model.repository.Repository
import retrofit2.Response

class HomeViewModel(private val repository: Repository) : BaseViewModel() {

    val movies = MutableLiveData<List<Movie>>()

    fun getMovies(){
        repository.getMovies().value?.let { localMovies ->
            movies.postValue(localMovies)
        }
        dispatchWeb(object: WebDispatcher<PaginatedResponse<Movie>>(){
            override suspend fun execute(): Response<PaginatedResponse<Movie>> {
                return repository.getPopularMovies(BuildConfig.API_KEY, 1)
            }
        })
        { response ->
            val body = response.body()
                body?.results?.let { list ->
                    movies.postValue(list)
                    insertLocalMovies(list)
                }
        }
    }

    private fun insertLocalMovies(movies: List<Movie>){
        dispatchDb(object : Dispatcher(){
            override suspend fun execute() {
                repository.deleteAllMovies()
                repository.addMovies(movies)
            }
        })
    }

}