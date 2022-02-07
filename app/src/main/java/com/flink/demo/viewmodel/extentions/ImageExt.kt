package com.flink.demo.viewmodel.extentions

import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ImageView.loadImage(url: String?, thumb: Int, callback: Callback?) {
    Picasso.get()
        .load(url)
        .placeholder(thumb)
        .error(thumb)
        .into(this, callback)
}

fun ImageView.loadImage(url: String, thumb: Int) {
    Log.i("image", url)
    Picasso.get()
        .load(url)
        .placeholder(thumb)
        .error(thumb)
        .into(this)
}