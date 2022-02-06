package com.flink.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.flink.demo.BuildConfig
import com.flink.demo.model.data.response.Movie
import com.flink.demo.model.data.response.PaginatedResponse
import com.flink.demo.model.data.response.TopMovie
import com.flink.demo.model.repository.Repository
import retrofit2.Response

class TopMoviesViewModel(private val repository: Repository) : BaseViewModel() {

    val topMovies = MutableLiveData<List<TopMovie>>()

    fun getMovies(){
        repository.getTopMovies().value?.let { localMovies ->
            topMovies.postValue(localMovies)
        }
        dispatchWeb(object: WebDispatcher<PaginatedResponse<Movie>>(){
            override suspend fun execute(): Response<PaginatedResponse<Movie>> {
                return repository.getPopularMovies(BuildConfig.API_KEY, 1)
            }
        })
        { response ->
            val body = response.body()
                body?.results?.let { list ->
                    val topResults = list.map { TopMovie(it.id, it) }
                    topMovies.postValue(topResults)
                    insertLocalMovies(topResults)
                }
        }
    }

    private fun insertLocalMovies(movies: List<TopMovie>){
        dispatchDb(object : Dispatcher(){
            override suspend fun execute() {
                repository.deleteAllTopMovies()
                repository.addTopMovies(movies)
            }
        })
    }

}