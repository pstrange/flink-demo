package com.flink.demo.view.adapters.cardstop

import androidx.lifecycle.MutableLiveData
import com.flink.demo.R
import com.flink.demo.databinding.ItemViewCardBinding
import com.flink.demo.model.data.response.Movie
import com.flink.demo.view.adapters.base.BaseViewHolder
import com.flink.demo.viewmodel.extentions.loadImage
import com.flink.demo.viewmodel.preferences.AppPreferences

class CardItemHolder(binder: ItemViewCardBinding)
    : BaseViewHolder<Movie, ItemViewCardBinding>(binder){

    var bookmarkChanges : MutableLiveData<Movie>? = null
    var parentPosition : Int = 0

    override fun onBindViewHolder(item: Movie) {
        super.onBindViewHolder(item)
        val isBookmarked = AppPreferences.BOOKMAKS.contains(item.id)
        binder.movie = item
        binder.movie?.isBookmark = isBookmarked
        binder.textDate.text = item.release_date
        binder.textTitle.text = item.title
        binder.buttonBookmark.setImageResource(
            if(isBookmarked) R.drawable.ic_action_favorite
            else R.drawable.ic_action_favorite_border )
        binder.buttonBookmark.setOnClickListener {
            val value = !binder.movie?.isBookmark!!
            binder.movie?.isBookmark = value
            binder.buttonBookmark.setImageResource(
                if(value) R.drawable.ic_action_favorite
                else R.drawable.ic_action_favorite_border )
            bookmarkChanges?.postValue(binder.movie)
        }
        binder.imageThumb.loadImage(AppPreferences.IMAGE_PATH_BIG+item.poster_path, R.drawable.img_thumb)
    }

}