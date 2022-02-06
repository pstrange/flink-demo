package com.flink.demo.view.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.flink.demo.R
import com.flink.demo.databinding.FragmentRatedMoviesBinding
import com.flink.demo.model.data.response.Error
import com.flink.demo.model.data.response.RateMovie
import com.flink.demo.viewmodel.RatedMoviesViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RatedMoviesFragment : BaseFragment<FragmentRatedMoviesBinding>() {

    private val viewModel: RatedMoviesViewModel by viewModel { parametersOf() }

    override fun getLayout(): Int = R.layout.fragment_rated_movies

    override fun initViews(view: View, savedInstanceState: Bundle?) {

    }

    override fun initViewModel(view: View, savedInstanceState: Bundle?) {
        viewModel.ratedMovies.observe(this, moviesObserver)
        viewModel.error.observe(this, errorObserver)

        viewModel.getMovies()
    }

    private val moviesObserver = Observer<List<RateMovie>?> { movies ->
        binding.textView.text = Gson().toJson(movies)
    }

    private val errorObserver = Observer<Error> { error ->
        binding.textView.text = error.status_message
    }
}