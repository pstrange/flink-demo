package com.flink.demo.view.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import com.flink.demo.R
import com.flink.demo.databinding.ItemIndicatorBinding
import com.flink.demo.databinding.ViewRecyclerIndicatorBinding
import com.flink.demo.view.adapters.cardstop.CardsAdapter
import com.flink.demo.view.adapters.snaphelper.OnSnapPositionChangeListener

class IndicatorView : FrameLayout, OnSnapPositionChangeListener {

    constructor(context: Context) : super(context){
        initalize(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet){
        initalize(context)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr){
        initalize(context)
    }

    lateinit var binder: ViewRecyclerIndicatorBinding
    var selectedItem: Int = 0
    var adapter: CardsAdapter? = null
    set(value) {
        binder.layoutContainer.removeAllViews()
        selectedItem = 0
        value?.items?.forEachIndexed { index, _ ->
            addIndicatorItem(index)
        }
        field = value
    }

    private fun initalize(context: Context) {
        binder = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_recycler_indicator, this, true)
    }

    private fun addIndicatorItem(i: Int){
        val itemBinder = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_indicator, binder.layoutContainer, true
        ) as ItemIndicatorBinding
        if(i == selectedItem) selectItemView(itemBinder.layoutContainer)
    }

    private fun clearSelection(){
        binder.layoutContainer.children.iterator().forEach { view ->
            view.isSelected = false
        }
    }

    private fun selectItemView(view: View){
        view.isSelected = true
    }

    override fun onSnapPositionChange(position: Int) {
        clearSelection()
        selectItemView(binder.layoutContainer.getChildAt(position))
        selectedItem = position
    }
}