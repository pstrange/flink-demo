package com.flink.demo.viewmodel.extentions

import android.content.res.Resources
import kotlin.math.abs

val Int.toPx: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.toDp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.convertOffsetToPercent(scrollRange: Int, verticalOffset: Int, skipValue: Int? = 0) : Float{
    val absOffset = abs(verticalOffset)
    val percent = if(absOffset >= skipValue!!)
        (absOffset-skipValue).toFloat() / (scrollRange-skipValue).toFloat()
    else 0f
    return if(percent > 1) 1f else percent
}