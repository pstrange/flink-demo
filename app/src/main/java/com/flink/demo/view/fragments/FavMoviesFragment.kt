package com.flink.demo.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.flink.demo.R
import com.flink.demo.databinding.FragmentFavMoviesBinding
import com.flink.demo.model.data.request.CoverElement
import com.flink.demo.model.data.response.Movie
import com.flink.demo.view.adapters.covers.CoverAdapter
import com.flink.demo.viewmodel.FavMoviesViewModel
import com.flink.demo.viewmodel.preferences.AppPreferences
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FavMoviesFragment : BaseFragment<FragmentFavMoviesBinding>() {

    private val viewModel: FavMoviesViewModel by viewModel { parametersOf() }

    override fun getLayout(): Int = R.layout.fragment_fav_movies

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun initViewModel(view: View, savedInstanceState: Bundle?) {
        viewModel.favMovies.observe(this, moviesObserver)

        viewModel.getMovies()
    }

    private val moviesObserver = Observer<List<Movie?>> { movies ->
        if(movies.isNullOrEmpty())
            binding.emptyView.visibility = View.VISIBLE
        else
            binding.emptyView.visibility = View.GONE

        val coverItems = ArrayList<CoverElement>()
        viewModel.parseListElements(movies, coverItems)

        binding.adapter = CoverAdapter()
        binding.adapter?.bookmarkChanges?.observe(this, bookmarksObserver)
        binding.adapter?.itemSelector?.observe(this, itemSelectedObserver)
        binding.adapter?.items = coverItems
        binding.recyclerView.adapter = binding.adapter
    }

    private val bookmarksObserver = Observer<Movie> {
        val exist = AppPreferences.BOOKMAKS.contains(it.id)
        if(exist) {
            viewModel.deleteFavMovies(it)
        }
    }

    private val itemSelectedObserver = Observer<Pair<CoverElement, Int>> { pair ->
        val item = pair.first
        val movie = item.data as Movie
        Log.i("click", "goto next "+movie.title)
    }
}