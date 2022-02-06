package com.flink.demo.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.flink.demo.R
import com.flink.demo.databinding.ActivitySplashBinding
import com.flink.demo.model.data.response.Error
import com.flink.demo.model.data.response.GuestSession
import com.flink.demo.viewmodel.SplashViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val viewModel: SplashViewModel by viewModel { parametersOf() }

    override fun getLayout(): Int = R.layout.activity_splash

    override fun initViews(savedInstanceState: Bundle?) {
        binding.textView.text = "NetFlink"
    }

    override fun initViewModel(savedInstanceState: Bundle?) {
        viewModel.session.observe(this, sessionObserver)
        viewModel.error.observe(this, errorObserver)

        viewModel.authenticate()
    }

    private val sessionObserver = Observer<GuestSession?> { auth ->
        binding.textView.text = Gson().toJson(auth)
        startActivity(Intent(this, HomeActivity::class.java))
    }

    private val errorObserver = Observer<Error> { error ->
        binding.textView.text = error.status_message
    }

}