package com.flink.demo.view.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import com.flink.demo.R
import com.flink.demo.databinding.ActivityHomeBinding
import com.flink.demo.model.data.response.Error
import com.flink.demo.model.data.response.TopMovie
import com.flink.demo.viewmodel.TopMoviesViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun getLayout(): Int = R.layout.activity_home

    override fun initViews(savedInstanceState: Bundle?) {
        binding.textView.text = "HOME NetFlink"
    }

    override fun initViewModel(savedInstanceState: Bundle?) {}

}