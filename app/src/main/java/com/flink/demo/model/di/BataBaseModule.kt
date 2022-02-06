package com.flink.demo.model.di

import android.app.Application
import androidx.room.Room
import com.flink.demo.model.local.LocalDataSource
import com.flink.demo.model.local.MoviesDB
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataBaseModule = module {

    fun provideDataBase(application: Application): MoviesDB {
        return Room.databaseBuilder(application, MoviesDB::class.java, "MOVIESDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(dataBase: MoviesDB): LocalDataSource {
        return dataBase.moviesDao
    }

    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }

}