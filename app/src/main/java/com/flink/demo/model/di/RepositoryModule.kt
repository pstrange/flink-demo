package com.flink.demo.model.di

import com.flink.demo.model.repository.Repository
import com.flink.demo.model.repository.RepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<Repository> { RepositoryImpl(service = get()) }
}