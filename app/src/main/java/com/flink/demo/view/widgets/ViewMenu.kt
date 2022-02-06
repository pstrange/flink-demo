package com.flink.demo.view.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import com.flink.demo.R
import com.flink.demo.databinding.ViewMenuBinding

class ViewMenu : FrameLayout, View.OnClickListener{

    constructor(context: Context) : super(context){
        initalize(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet){
        initalize(context)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr){
        initalize(context)
    }

    private lateinit var binder: ViewMenuBinding
    var listener: MenuViewListener? = null
    var selectedItem: MenuItem = MenuItem.TOP

    private fun initalize(context: Context) {
        binder = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_menu, this, true)

        binder.itemTop.tag = MenuItem.TOP
        binder.itemRate.tag = MenuItem.RATE
        binder.itemFav.tag = MenuItem.FAV

        binder.itemTop.setOnClickListener(this)
        binder.itemRate.setOnClickListener(this)
        binder.itemFav.setOnClickListener(this)

        binder.itemTop.getChildAt(0).visibility = View.VISIBLE
        binder.itemTop.isSelected = true
    }

    private fun clearSelection(){
        unselectItem(binder.itemTop)
        unselectItem(binder.itemRate)
        unselectItem(binder.itemFav)
    }

    private fun unselectItem(item: LinearLayout){
        item.isSelected = false
        item.getChildAt(0).visibility = View.GONE
        item.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
    }

    override fun onClick(view: View?) {
        val selectedView = (view as LinearLayout)
        val menuItem = selectedView.tag as MenuItem
        when {
            menuItem != selectedItem -> {
                clearSelection()
                selectedView.isSelected = true
                selectedView.getChildAt(0).visibility = View.VISIBLE
                selectedView.layoutParams = LinearLayout
                    .LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
                selectedItem = menuItem
                listener?.onMenuOptionSelected(menuItem)
            }
            else -> listener?.onMenuOptionReselected(selectedItem)
        }
    }

    enum class MenuItem(val value: Int) {
        TOP(0),
        RATE(1),
        FAV(2)
    }

    interface MenuViewListener{
        fun onMenuOptionSelected(item: MenuItem)
        fun onMenuOptionReselected(item: MenuItem)
    }
}