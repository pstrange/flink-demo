package com.flink.demo.model.di

import com.flink.demo.viewmodel.FavMoviesViewModel
import com.flink.demo.viewmodel.RatedMoviesViewModel
import com.flink.demo.viewmodel.TopMoviesViewModel
import com.flink.demo.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { TopMoviesViewModel(get()) }
    viewModel { RatedMoviesViewModel(get()) }
    viewModel { FavMoviesViewModel(get()) }
}