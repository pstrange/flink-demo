package com.flink.demo.viewmodel

import androidx.lifecycle.MutableLiveData
import com.flink.demo.BuildConfig
import com.flink.demo.model.data.request.CoverElement
import com.flink.demo.model.data.response.FavMovie
import com.flink.demo.model.data.response.Movie
import com.flink.demo.model.data.response.PaginatedResponse
import com.flink.demo.model.data.response.TopMovie
import com.flink.demo.model.repository.Repository
import com.flink.demo.viewmodel.preferences.AppPreferences
import retrofit2.Response

class TopMoviesViewModel(private val repository: Repository) : BaseViewModel() {

    val topMovies = MutableLiveData<List<Movie?>>()
    val topMoviesPager = MutableLiveData<List<Movie?>>()

    private fun getFavMoviesIds(){
        val ids = repository.getFavoritesIds()
        AppPreferences.BOOKMAKS = ids
    }

    fun insertFavMovies(movie: Movie){
        dispatchDb(object : Dispatcher(){
            override suspend fun execute() {
                repository.addFavorite(FavMovie(movieId = movie.id, movie = movie))
                getFavMoviesIds()
            }
        })
    }

    fun deleteFavMovies(movie: Movie){
        dispatchDb(object : Dispatcher(){
            override suspend fun execute() {
                repository.deleteFavorite(FavMovie(movieId = movie.id, movie = movie))
                getFavMoviesIds()
            }
        })
    }

    fun getMovies(){
        dispatchWeb(object: WebDispatcher<PaginatedResponse<Movie>>(){
            override suspend fun execute(): Response<PaginatedResponse<Movie>> {
//                val savedMovies = repository.getTopMovies().map { it.movie }
//                    topMovies.postValue(savedMovies)
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

    fun parseHorizontalElement(
        elements: List<Movie?>,
        items : ArrayList<CoverElement>){

        if(!elements.isNullOrEmpty()){
            items.add(
                CoverElement(
                type = CoverElement.Type.CARDS,
                data = ArrayList(elements))
            )
        }
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