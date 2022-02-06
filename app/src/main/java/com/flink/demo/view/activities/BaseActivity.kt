package com.flink.demo.view.activities

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B: ViewDataBinding> : AppCompatActivity() {

    private fun <B> binding(@LayoutRes resId: Int) : Lazy<B> =
        lazy { DataBindingUtil.setContentView(this, resId) }

    val binding by binding<B>(this.getLayout())

    abstract fun getLayout(): Int
    abstract fun initViews(savedInstanceState: Bundle?)
    abstract fun initViewModel(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews(savedInstanceState)
        initViewModel(savedInstanceState)
    }

}