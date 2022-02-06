package com.flink.demo

import android.app.Application
import com.flink.demo.model.di.*
import com.flink.demo.viewmodel.preferences.AppPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NetFlinkApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this@NetFlinkApp)
        startKoin {
            androidContext(this@NetFlinkApp)
            modules(
                imagesProcessorModule,
                networkModule,
                dataBaseModule,
                repositoryModule,
                viewModelModule)
        }
    }

}