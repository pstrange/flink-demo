package com.flink.demo.view.adapters.covers

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.flink.demo.databinding.ItemCoverCardsTopBinding
import com.flink.demo.model.data.request.Bookmark
import com.flink.demo.model.data.request.CoverElement
import com.flink.demo.model.data.response.Movie
import com.flink.demo.view.adapters.base.BaseViewHolder
import com.flink.demo.view.adapters.base.OnRecyclerItemClick
import com.flink.demo.view.adapters.cardstop.CardsAdapter
import com.flink.demo.view.adapters.snaphelper.SnapOnScrollListener
import com.flink.demo.viewmodel.extentions.attachSnapHelperWithListener

class CoverItemCardsHolder(binder: ItemCoverCardsTopBinding) :
    BaseViewHolder<CoverElement, ItemCoverCardsTopBinding>(binder), OnRecyclerItemClick<Movie> {

    var itemSelectionLiveData: MutableLiveData<Pair<CoverElement, Int>>? = null
    var bookmarkChanges : MutableLiveData<Bookmark>? = null
    var adapter : CardsAdapter? = null

    init {
        binder.recyclerCards.layoutManager = LinearLayoutManager(binder.root.context, LinearLayoutManager.HORIZONTAL, false)
        binder.recyclerCards.attachSnapHelperWithListener(
            snapHelper = LinearSnapHelper(),
            behavior = SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
            onSnapPositionChangeListener = binder.indicatorView
        )
    }

    override fun onBindViewHolder(item: CoverElement) {
        super.onBindViewHolder(item)
        adapter = CardsAdapter()
        adapter?.onRecyclerItemClick = this
        adapter?.bookmarkChanges = bookmarkChanges
        adapter?.parentPosition = adapterPosition
        adapter?.items = item.data as ArrayList<Movie>
        binder.recyclerCards.adapter = adapter
        binder.indicatorView.adapter = adapter
    }

    fun notifyToVisibleItems(){
        adapter?.notifyToVisibleItems()
    }

    fun notifyItemChanged(position: Int){
        binder.recyclerCards.adapter?.notifyItemChanged(position)
    }

    override fun onItemClick(view: View?, movie: Movie?, position: Int) {
        itemSelectionLiveData?.postValue(Pair(CoverElement(CoverElement.Type.CARDS, movie), position))
    }
}