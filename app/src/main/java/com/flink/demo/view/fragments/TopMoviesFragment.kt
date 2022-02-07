package com.flink.demo.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.flink.demo.R
import com.flink.demo.databinding.FragmentTopMoviesBinding
import com.flink.demo.model.data.request.CoverElement
import com.flink.demo.model.data.response.Movie
import com.flink.demo.view.adapters.covers.CoverAdapter
import com.flink.demo.view.widgets.OnRecyclerScrollListener
import com.flink.demo.viewmodel.TopMoviesViewModel
import com.flink.demo.viewmodel.preferences.AppPreferences
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class TopMoviesFragment : BaseFragment<FragmentTopMoviesBinding>() {

    private val viewModel: TopMoviesViewModel by viewModel { parametersOf() }

    override fun getLayout(): Int = R.layout.fragment_top_movies

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.addOnScrollListener(onScrollListener)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getMovies()
        }
    }

    override fun initViewModel(view: View, savedInstanceState: Bundle?) {
        viewModel.topMovies.observe(this, moviesObserver)
        viewModel.topMoviesPager.observe(this, sectionPagerObserver)
        viewModel.isLoading.observe(this, loaderObserver)

        viewModel.getMovies()
    }

    private val moviesObserver = Observer<List<Movie?>> { movies ->
        val coverItems = ArrayList<CoverElement>()
        if(movies.size > 5){
            val topCovers = movies.subList(0, 5)
            viewModel.parseHorizontalElement(topCovers, coverItems)
        }
        if(movies.size > 6){
            val topRest = movies.subList(5, movies.size)
            viewModel.parseListElements(topRest, coverItems)
        }
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
        val navController = Navigation.findNavController(requireActivity(), R.id.fragment_navigation_container)
            navController.navigate(R.id.detailActivity, bundleOf(
                "movie" to movie
            ))
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