package com.flink.demo.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B: ViewDataBinding>  : Fragment() {

    lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view, savedInstanceState)
        initViewModel(view, savedInstanceState)
    }

    abstract fun getLayout(): Int
    abstract fun initViews(view: View, savedInstanceState: Bundle?)
    abstract fun initViewModel(view: View, savedInstanceState: Bundle?)

}