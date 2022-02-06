package com.flink.demo.view.activities

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.flink.demo.R
import com.flink.demo.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding>(){

    override fun getLayout(): Int = R.layout.activity_home

    override fun initViews(savedInstanceState: Bundle?) {
        val navController = Navigation.findNavController(this, R.id.fragment_navigation_container)
        NavigationUI.setupWithNavController(binding.navigationMenu, navController)
    }

    override fun initViewModel(savedInstanceState: Bundle?) {}

}