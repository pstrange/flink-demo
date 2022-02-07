package com.flink.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.flink.demo.BuildConfig
import com.flink.demo.model.data.response.Movie
import com.flink.demo.model.data.response.PaginatedResponse
import com.flink.demo.model.data.response.TopMovie
import com.flink.demo.model.repository.Repository
import retrofit2.Response

class TopMoviesViewModel(private val repository: Repository) : BaseViewModel() {

    val bookmarks = MutableLiveData<List<Long>>()
    val bookmarksResult = MutableLiveData<List<Long>>()
    val topMovies = MutableLiveData<List<Movie?>>()
    val topMoviesPager = MutableLiveData<List<Movie?>>()

    fun getMovies(){
        repository.getTopMovies().value?.let { localMovies ->
            val savedMovies = localMovies.map { it.movie }
            topMovies.postValue(savedMovies)
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
                    topMovies.postValue(list)
                    insertLocalMovies(topResults)
                }
        }
    }

    fun getMoviesPage(page: Int){
        dispatchWeb(object: WebDispatcher<PaginatedResponse<Movie>>(){
            override suspend fun execute(): Response<PaginatedResponse<Movie>> {
                return repository.getPopularMovies(BuildConfig.API_KEY, page)
            }
        })
        { response ->
            val body = response.body()
            body?.results?.let { list ->
                topMoviesPager.postValue(list)
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