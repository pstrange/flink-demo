package com.flink.demo.model.di

import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val imagesProcessorModule = module {
    single {
        val picasso = Picasso.Builder(androidContext())
            .memoryCache(LruCache(androidContext()))
            .build()
        Picasso.setSingletonInstance(picasso)
    }
}