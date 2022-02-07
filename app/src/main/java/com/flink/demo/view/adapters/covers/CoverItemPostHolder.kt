package com.flink.demo.view.adapters.covers

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.flink.demo.BuildConfig
import com.flink.demo.R
import com.flink.demo.databinding.ItemCoverPostBinding
import com.flink.demo.model.data.request.Bookmark
import com.flink.demo.model.data.request.CoverElement
import com.flink.demo.model.data.response.Movie
import com.flink.demo.view.adapters.base.BaseViewHolder
import com.flink.demo.view.adapters.base.OnRecyclerItemClick
import com.flink.demo.viewmodel.extentions.loadImage
import com.flink.demo.viewmodel.preferences.AppPreferences

class CoverItemPostHolder(binder: ItemCoverPostBinding)
    : BaseViewHolder<CoverElement, ItemCoverPostBinding>(binder),
    OnRecyclerItemClick<CoverElement> {

    var bookmarkChanges : MutableLiveData<Bookmark>? = null
    var itemSelectionLiveData : MutableLiveData<Pair<CoverElement, Int>>? = null

    override fun onBindViewHolder(item: CoverElement) {
        super.onBindViewHolder(item)
        val movie = item.data as Movie
        onItemClick = this
        binder.movie = movie
        binder.movie!!.isBookmark = AppPreferences.BOOKMAKS.contains(binder.movie!!.id)
        binder.textDate.text = movie.release_date
        binder.textTitle.text = movie.title
        binder.buttonBookmark.setImageResource(
            if(binder.movie?.isBookmark == true) R.drawable.ic_action_favorite
            else R.drawable.ic_action_favorite_border )
        binder.buttonBookmark.setOnClickListener {
            binder.movie?.isBookmark = !binder.movie!!.isBookmark
            binder.buttonBookmark.setImageResource(
                if(binder.movie?.isBookmark == true) R.drawable.ic_action_favorite
                else R.drawable.ic_action_favorite_border )
            bookmarkChanges?.postValue(Bookmark(
                index = adapterPosition, movie = binder.movie))
        }
        binder.imageThumb.loadImage(BuildConfig.IMAGE_HOST+movie.poster_path, R.drawable.img_thumb)
    }

    override fun onItemClick(view: View?, item: CoverElement?, position: Int) {
        itemSelectionLiveData?.postValue(Pair(item!!, position))
    }
}