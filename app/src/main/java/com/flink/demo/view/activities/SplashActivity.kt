package com.flink.demo.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.constraintlayout.motion.widget.MotionLayout
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
class SplashActivity : BaseActivity<ActivitySplashBinding>(), MotionLayout.TransitionListener {

    private val viewModel: SplashViewModel by viewModel { parametersOf() }

    override fun getLayout(): Int = R.layout.activity_splash

    override fun initViews(savedInstanceState: Bundle?) {
        binding.apply {
            animationEnd = false
            serviceEnd = false
            motionLayout.setTransitionListener(this@SplashActivity)
        }
    }

    override fun initViewModel(savedInstanceState: Bundle?) {
        viewModel.apply {
            session.observe(this@SplashActivity, sessionObserver)
            error.observe(this@SplashActivity, errorObserver)
        }
        viewModel.authenticate()
    }

    private fun startNextActivity(){
        if(binding.serviceEnd == true && binding.animationEnd == true)
            startActivity(Intent(this, HomeActivity::class.java))
    }

    private val sessionObserver = Observer<GuestSession?> { auth ->
        binding.serviceEnd = true
        startNextActivity()
    }

    private val errorObserver = Observer<Error> { error ->
        binding.serviceEnd = true
        startNextActivity()
    }

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
        binding.animationEnd = true
        startNextActivity()
    }

    override fun onTransitionStarted(
        motionLayout: MotionLayout?, startId: Int, endId: Int) {}

    override fun onTransitionChange(
        motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {}

    override fun onTransitionTrigger(
        motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}


}