package com.flink.demo.view.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.flink.demo.R
import com.flink.demo.databinding.FragmentTopMoviesBinding
import com.flink.demo.model.data.response.Error
import com.flink.demo.model.data.response.TopMovie
import com.flink.demo.viewmodel.TopMoviesViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TopMoviesFragment : BaseFragment<FragmentTopMoviesBinding>() {

    private val viewModel: TopMoviesViewModel by viewModel { parametersOf() }

    override fun getLayout(): Int = R.layout.fragment_top_movies

    override fun initViews(view: View, savedInstanceState: Bundle?) {

    }

    override fun initViewModel(view: View, savedInstanceState: Bundle?) {
        viewModel.topMovies.observe(this, moviesObserver)
        viewModel.error.observe(this, errorObserver)

        viewModel.getMovies()
    }

    private val moviesObserver = Observer<List<TopMovie>?> { movies ->
        binding.textView.text = Gson().toJson(movies)
    }

    private val errorObserver = Observer<Error> { error ->
        binding.textView.text = error.status_message
    }
}