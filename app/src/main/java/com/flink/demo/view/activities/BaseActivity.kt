package com.flink.demo.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B: ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: B

    abstract fun getLayout(): Int
    abstract fun initViews(savedInstanceState: Bundle?)
    abstract fun initViewModel(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayout())
        initViews(savedInstanceState)
        initViewModel(savedInstanceState)
    }

}