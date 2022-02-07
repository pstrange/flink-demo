package com.flink.demo.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.flink.demo.R
import com.flink.demo.databinding.FragmentTopMoviesBinding
import com.flink.demo.model.data.request.Bookmark
import com.flink.demo.model.data.request.CoverElement
import com.flink.demo.model.data.response.Error
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
        viewModel.error.observe(this, errorObserver)

        viewModel.getMovies()
    }

    private val moviesObserver = Observer<List<Movie?>> { movies ->
        val topCovers = movies.subList(0, 5)
        val topRest = movies.subList(5, movies.size)

        val coverItems = ArrayList<CoverElement>()
        parseHorizontalElement(topCovers, coverItems)
        parseListElements(topRest, coverItems)

        binding.adapter = CoverAdapter()
        binding.adapter?.bookmarkChanges?.observe(this, bookmarksObserver)
        binding.adapter?.itemSelector?.observe(this, itemSelectedObserver)
        viewModel.bookmarks.observe(this, bookmarksChangeObserver)
        binding.adapter?.items = coverItems
        binding.recyclerView.adapter = binding.adapter!!
    }

    private val sectionPagerObserver = Observer<List<Movie?>> { result ->
        val coverItems = ArrayList<CoverElement>()
        parseListElements(
            result,
            coverItems)
        if(!onScrollListener.isFinalPage){
            binding.adapter?.items?.addAll(coverItems)
            binding.adapter?.notifyItemRangeChanged(
                binding.adapter?.items?.size!!-15, binding.adapter?.items?.size!!)
        }
    }

    private val bookmarksObserver = Observer<Bookmark> {
        binding.adapter?.notifyToVisibleItems()
    }

    private val bookmarksChangeObserver = Observer<List<Long>> {
        viewModel.bookmarksResult.postValue(AppPreferences.BOOKMAKS)
    }

    private val itemSelectedObserver = Observer<Pair<CoverElement, Int>> { pair ->
        val coverItem = pair.first
        val movie = coverItem.data as Movie
        Log.i("click", "goto next "+movie.title)
    }

    private val errorObserver = Observer<Error> { error ->
//        binding.textView.text = error.status_message
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

    private fun parseHorizontalElement(
        elements: List<Movie?>,
        items : ArrayList<CoverElement>){

        if(!elements.isNullOrEmpty()){
            items.add(CoverElement(
                type = CoverElement.Type.CARDS,
                data = ArrayList(elements)))
        }
    }

    private fun parseListElements(
        elements: List<Movie?>,
        items : ArrayList<CoverElement>){

        if(!elements.isNullOrEmpty()){
            elements.forEach { movie ->
                items.add(CoverElement(
                    type = CoverElement.Type.ITEM_POST,
                    data = movie))
            }
        }
    }
}