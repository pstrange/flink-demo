package com.flink.demo.view.adapters.covers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flink.demo.R
import com.flink.demo.model.data.request.Bookmark
import com.flink.demo.model.data.request.CoverElement
import com.flink.demo.model.data.response.Movie
import com.flink.demo.view.adapters.base.BaseAdapter
import com.flink.demo.view.adapters.base.BaseViewHolder
import com.flink.demo.viewmodel.preferences.AppPreferences

@Suppress("UNCHECKED_CAST")
class CoverAdapter : BaseAdapter<CoverElement, ViewDataBinding>() {

    var recyclerView : RecyclerView? = null
    var showLoader : Boolean = false
        set(value) {
            if(value && !field)
                addLoaderItem()
            else if(field && !value)
                removeLoaderItem()
            field = value
        }

    val bookmarkChanges = MutableLiveData<Movie>()
    val itemSelector = MutableLiveData<Pair<CoverElement, Int>>()

    override fun getItemViewType(position: Int): Int {
        return items!![position].type?.value!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : BaseViewHolder<CoverElement, ViewDataBinding> {
        return when (viewType){
            CoverElement.Type.CARDS.value ->{
                val holder = CoverItemCardsHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_cover_cards_top, parent, false
                    )
                )
                holder.bookmarkChanges = bookmarkChanges
                holder.itemSelectionLiveData = itemSelector
                holder as BaseViewHolder<CoverElement, ViewDataBinding>
            }
            CoverElement.Type.LOADER.value ->{
                CoverItemLoaderHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_cover_loader, parent, false
                    )
                ) as BaseViewHolder<CoverElement, ViewDataBinding>
            }
            else ->{
                val holder = CoverItemPostHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_cover_post, parent, false
                    )
                )
                holder.bookmarkChanges = bookmarkChanges
                holder.itemSelectionLiveData = itemSelector
                holder as BaseViewHolder<CoverElement, ViewDataBinding>
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private fun addLoaderItem(){
        items?.add(CoverElement(type = CoverElement.Type.LOADER))
        notifyItemChanged(items?.size!! - 1)
    }

    private fun removeLoaderItem(){
        val item = items?.find { it.type == CoverElement.Type.LOADER }
        val indexOf = items?.indexOf(item)
        indexOf?.let { if(it > 0){
            items?.removeAt(indexOf)
            notifyItemChanged(it)
        }}
    }

    fun removeItem(index: Int){
        items?.removeAt(index)
        notifyItemRemoved(index)
    }

    fun notifyToVisibleItems(){
        this.recyclerView?.layoutManager?.let { layoutManager ->
            layoutManager as LinearLayoutManager
            val firstPosition = layoutManager.findFirstVisibleItemPosition()
            val lastPosition = layoutManager.findLastVisibleItemPosition()
            if(firstPosition >= 0 && lastPosition >= 0)
                for(index in firstPosition .. lastPosition){
                    val item = items!![index]
                    if(item.type == CoverElement.Type.ITEM_POST){
                        val movie = item.data as Movie
                        if(movie.isBookmark && !AppPreferences.BOOKMAKS.contains(movie.id) ||
                          !movie.isBookmark && AppPreferences.BOOKMAKS.contains(movie.id))
                            notifyItemChanged(index)
                    }else if(item.type == CoverElement.Type.CARDS){
                        val holder = this.recyclerView?.findViewHolderForAdapterPosition(index)
                        if(holder is CoverItemCardsHolder)
                            holder.notifyToVisibleItems()
                    }
                }
        }
    }
}