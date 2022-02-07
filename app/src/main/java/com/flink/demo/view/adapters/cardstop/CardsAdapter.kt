package com.flink.demo.view.adapters.cardstop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flink.demo.R
import com.flink.demo.databinding.ItemViewCardBinding
import com.flink.demo.model.data.response.Movie
import com.flink.demo.view.adapters.base.BaseAdapter
import com.flink.demo.view.adapters.base.BaseViewHolder
import com.flink.demo.viewmodel.preferences.AppPreferences

class CardsAdapter : BaseAdapter<Movie, ItemViewCardBinding>(){

    var recyclerView : RecyclerView? = null
    var bookmarkChanges : MutableLiveData<Movie>? = null
    var parentPosition : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : BaseViewHolder<Movie, ItemViewCardBinding> {
        val holder = CardItemHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_view_card, parent, false
            )
        )
        holder.parentPosition = parentPosition
        holder.bookmarkChanges = bookmarkChanges
        return holder
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    fun notifyToVisibleItems(){
        this.recyclerView?.layoutManager?.let { layoutManager ->
            layoutManager as LinearLayoutManager
            val firstPosition = layoutManager.findFirstVisibleItemPosition()
            val lastPosition = layoutManager.findLastVisibleItemPosition()
            if(firstPosition >= 0 && lastPosition >= 0)
                for(index in firstPosition .. lastPosition){
                    if(items!![index].isBookmark && !AppPreferences.BOOKMAKS.contains(items!![index].id) ||
                      !items!![index].isBookmark && AppPreferences.BOOKMAKS.contains(items!![index].id))
                        notifyItemChanged(index)
                }
        }
    }
}