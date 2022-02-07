package com.flink.demo.view.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.flink.demo.R
import com.flink.demo.databinding.FragmentFavMoviesBinding
import com.flink.demo.model.data.response.Error
import com.flink.demo.model.data.response.FavMovie
import com.flink.demo.model.data.response.Movie
import com.flink.demo.viewmodel.FavMoviesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavMoviesFragment : BaseFragment<FragmentFavMoviesBinding>() {

    private val viewModel: FavMoviesViewModel by viewModel { parametersOf() }

    override fun getLayout(): Int = R.layout.fragment_fav_movies

    override fun initViews(view: View, savedInstanceState: Bundle?) {

    }

    override fun initViewModel(view: View, savedInstanceState: Bundle?) {
        viewModel.favMovies.observe(this, moviesObserver)
        viewModel.error.observe(this, errorObserver)

//        viewModel.getMovies()
    }

    private val moviesObserver = Observer<List<Movie?>> { movies ->
//        binding.textView.text = Gson().toJson(movies)
    }

    private val errorObserver = Observer<Error> { error ->
//        binding.textView.text = error.status_message
    }
}