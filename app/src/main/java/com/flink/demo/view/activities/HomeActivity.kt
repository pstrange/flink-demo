package com.flink.demo.view.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import com.flink.demo.R
import com.flink.demo.databinding.ActivityHomeBinding
import com.flink.demo.model.data.response.Error
import com.flink.demo.model.data.response.Movie
import com.flink.demo.viewmodel.HomeViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val viewModel: HomeViewModel by viewModel { parametersOf() }

    override fun getLayout(): Int = R.layout.activity_home

    override fun initViews(savedInstanceState: Bundle?) {
        binding.textView.text = "HOME NetFlink"
    }

    override fun initViewModel(savedInstanceState: Bundle?) {
        viewModel.movies.observe(this, moviesObserver)
        viewModel.error.observe(this, errorObserver)

        viewModel.getMovies()
    }

    private val moviesObserver = Observer<List<Movie>?> { movies ->
        binding.textView.text = Gson().toJson(movies)
    }

    private val errorObserver = Observer<Error> { error ->
        binding.textView.text = error.status_message
    }

}