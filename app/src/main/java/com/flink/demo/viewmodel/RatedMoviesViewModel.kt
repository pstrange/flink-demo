package com.flink.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.flink.demo.BuildConfig
import com.flink.demo.model.data.response.Movie
import com.flink.demo.model.data.response.PaginatedResponse
import com.flink.demo.model.data.response.RateMovie
import com.flink.demo.model.repository.Repository
import retrofit2.Response

class RatedMoviesViewModel(private val repository: Repository) : BaseViewModel() {

    val ratedMovies = MutableLiveData<List<RateMovie>>()

    fun getMovies(){
        repository.getRateMovies().value?.let { localMovies ->
            ratedMovies.postValue(localMovies)
        }
        dispatchWeb(object: WebDispatcher<PaginatedResponse<Movie>>(){
            override suspend fun execute(): Response<PaginatedResponse<Movie>> {
                return repository.getPopularMovies(BuildConfig.API_KEY, 1)
            }
        })
        { response ->
            val body = response.body()
                body?.results?.let { list ->
                    val topResults = list.map { RateMovie(it.id, it) }
                    ratedMovies.postValue(topResults)
                    insertLocalMovies(topResults)
                }
        }
    }

    private fun insertLocalMovies(movies: List<RateMovie>){
        dispatchDb(object : Dispatcher(){
            override suspend fun execute() {
                repository.deleteAllRateMovies()
                repository.addRateMovies(movies)
            }
        })
    }

}