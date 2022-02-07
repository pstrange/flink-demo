package com.flink.demo.view.widgets

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class OnRecyclerScrollListener : RecyclerView.OnScrollListener(){

    var nextPage: Int = 2
    var isFinalPage: Boolean = false

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if(!isFinalPage){
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
            linearLayoutManager?.let { layoutManager ->
                val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                recyclerView.adapter?.let { adapter ->
                    if (lastItem == adapter.itemCount - 1 ||
                        lastItem == adapter.itemCount - 2 ||
                        lastItem == adapter.itemCount - 3) {
                        loadMoreItems(nextPage)
                    }
                }
            }
        }
    }

    fun incrementPage(){
        nextPage++
    }

    fun decrementPage(){
        nextPage--
    }

    fun resetPage(){
        nextPage = 2
        isFinalPage = false
    }

    abstract fun loadMoreItems(nextPage: Int)
}