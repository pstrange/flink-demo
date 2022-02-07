package com.flink.demo.view.adapters.covers

import android.view.View
import com.flink.demo.databinding.ItemCoverLoaderBinding
import com.flink.demo.model.data.request.CoverElement
import com.flink.demo.view.adapters.base.BaseViewHolder

class CoverItemLoaderHolder(binder: ItemCoverLoaderBinding) : BaseViewHolder<CoverElement, ItemCoverLoaderBinding>(binder){

    override fun onBindViewHolder(item: CoverElement) {
        super.onBindViewHolder(item)
        binder.progressBar.visibility = View.VISIBLE
    }

}