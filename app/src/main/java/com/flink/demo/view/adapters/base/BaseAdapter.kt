package com.flink.demo.view.adapters.base

import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, B: ViewDataBinding> : RecyclerView.Adapter<BaseViewHolder<T, B>>(){

    var onRecyclerItemClick: OnRecyclerItemClick<T>? = null

    var items: ArrayList<T>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        items?.let {
            return it.size
        } ?:run {
            return 0
        }
    }

    fun clear() {
        items?.clear()
        notifyDataSetChanged()
    }

    @CallSuper
    override fun onBindViewHolder(holder: BaseViewHolder<T, B>, position: Int) {
        holder.onItemClick = onRecyclerItemClick
        holder.onBindViewHolder(items!![position])
    }
}