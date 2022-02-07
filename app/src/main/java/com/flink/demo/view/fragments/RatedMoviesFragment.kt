package com.flink.demo.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.flink.demo.R
import com.flink.demo.databinding.FragmentRatedMoviesBinding
import com.flink.demo.model.data.request.CoverElement
import com.flink.demo.model.data.response.Movie
import com.flink.demo.view.adapters.covers.CoverAdapter
import com.flink.demo.view.widgets.OnRecyclerScrollListener
import com.flink.demo.viewmodel.RatedMoviesViewModel
import com.flink.demo.viewmodel.preferences.AppPreferences
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RatedMoviesFragment : BaseFragment<FragmentRatedMoviesBinding>() {

    private val viewModel: RatedMoviesViewModel by viewModel { parametersOf() }

    override fun getLayout(): Int = R.layout.fragment_rated_movies

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.addOnScrollListener(onScrollListener)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getMovies()
        }
    }

    override fun initViewModel(view: View, savedInstanceState: Bundle?) {
        viewModel.rateMovies.observe(this, moviesObserver)
        viewModel.rateMoviesPager.observe(this, sectionPagerObserver)
        viewModel.isLoading.observe(this, loaderObserver)

        viewModel.getMovies()
    }

    private val moviesObserver = Observer<List<Movie?>> { movies ->
        val coverItems = ArrayList<CoverElement>()
        viewModel.parseListElements(movies, coverItems)

        binding.adapter = CoverAdapter()
        binding.adapter?.bookmarkChanges?.observe(this, bookmarksObserver)
        binding.adapter?.itemSelector?.observe(this, itemSelectedObserver)
        binding.adapter?.items = coverItems
        binding.recyclerView.adapter = binding.adapter
    }

    private val sectionPagerObserver = Observer<List<Movie?>> { result ->
        val coverItems = ArrayList<CoverElement>()
        viewModel.parseListElements(
            result,
            coverItems)
        if(!onScrollListener.isFinalPage){
            binding.adapter?.items?.addAll(coverItems)
            binding.adapter?.notifyItemRangeChanged(
                binding.adapter?.items?.size!!-15, binding.adapter?.items?.size!!)
        }
    }

    private val bookmarksObserver = Observer<Movie> {
        val exist = AppPreferences.BOOKMAKS.contains(it.id)
        if(exist) viewModel.deleteFavMovies(it)
        else viewModel.insertFavMovies(it)
        binding.adapter?.notifyToVisibleItems()
    }

    private val itemSelectedObserver = Observer<Pair<CoverElement, Int>> { pair ->
        val item = pair.first
        val movie = item.data as Movie
        Log.i("click", "goto next "+movie.title)
    }

    private val loaderObserver = Observer<Boolean> {
        when {
            binding.adapter?.showLoader == true -> {
                binding.adapter?.showLoader = it
            }
            binding.swipeRefreshLayout.isRefreshing -> {
                binding.swipeRefreshLayout.isRefreshing = it
            }
        }
    }

    private val onScrollListener = object : OnRecyclerScrollListener(){
        override fun loadMoreItems(nextPage: Int) {
            if(binding.adapter?.showLoader == false){
                binding.adapter!!.showLoader = true
                viewModel.getMoviesPage(nextPage)
                incrementPage()
            }
        }
    }
}