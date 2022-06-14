package com.example.finalspace.di

import com.example.finalspace.data.episodeRepositoryModule
import com.example.finalspace.data.local.episodeDao
import com.example.finalspace.data.remote.episodeModule
import com.example.finalspace.data.remote.httpModule
import com.example.finalspace.domain.useCaseModule
import com.example.finalspace.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        platformModule(),
        episodeDao,
        httpModule,
        episodeModule,
        useCaseModule,
        episodeRepositoryModule
    )
}

// called by iOS etc
fun initKoin() = initKoin{}