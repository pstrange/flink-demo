package com.flink.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.flink.demo.BuildConfig
import com.flink.demo.model.data.response.*
import com.flink.demo.model.repository.Repository
import retrofit2.Response

class DetailViewModel(private val repository: Repository) : BaseViewModel() {

    val movie = MutableLiveData<Movie>()

    fun getMovie(movieId: String){
        dispatchWeb(object: WebDispatcher<Movie>(){
            override suspend fun execute(): Response<Movie> {
//                val savedMovies = repository.getRateMovies().map { it.movie }
//                    rateMovies.postValue(savedMovies)
                return repository.getMovieDetail(movieId, BuildConfig.API_KEY)
            }
        })
        { response ->
            val body = response.body()
            movie.postValue(body)
        }
    }

}