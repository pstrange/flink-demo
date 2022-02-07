package com.flink.demo.view.adapters.cardstop

import androidx.lifecycle.MutableLiveData
import com.flink.demo.BuildConfig
import com.flink.demo.R
import com.flink.demo.databinding.ItemViewCardBinding
import com.flink.demo.model.data.request.Bookmark
import com.flink.demo.model.data.response.Movie
import com.flink.demo.view.adapters.base.BaseViewHolder
import com.flink.demo.viewmodel.extentions.loadImage
import com.flink.demo.viewmodel.preferences.AppPreferences

class CardItemHolder(binder: ItemViewCardBinding)
    : BaseViewHolder<Movie, ItemViewCardBinding>(binder){

    var bookmarkChanges : MutableLiveData<Bookmark>? = null
    var parentPosition : Int = 0

    override fun onBindViewHolder(item: Movie) {
        super.onBindViewHolder(item)
        binder.movie = item
        binder.movie?.isBookmark = AppPreferences.BOOKMAKS.contains(binder.movie!!.id)
        binder.textDate.text = item.release_date
        binder.textTitle.text = item.title
        binder.buttonBookmark.setImageResource(
            if(binder.movie?.isBookmark == true) R.drawable.ic_action_favorite
            else R.drawable.ic_action_favorite_border )
        binder.buttonBookmark.setOnClickListener {
            binder.movie?.isBookmark = !binder.movie!!.isBookmark
            binder.buttonBookmark.setImageResource(
                if(binder.movie?.isBookmark == true) R.drawable.ic_action_favorite
                else R.drawable.ic_action_favorite_border )
            bookmarkChanges?.postValue(Bookmark(
                index = parentPosition, subIndex = adapterPosition, movie = binder.movie))
        }
        binder.imageThumb.loadImage(BuildConfig.IMAGE_HOST+item.poster_path, R.drawable.img_thumb)
    }

}