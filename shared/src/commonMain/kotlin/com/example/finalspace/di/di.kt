package com.example.finalspace.di

import com.example.finalspace.data.characterDao
import com.example.finalspace.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(
        platformModule(),
        characterDao,

    )
}

// called by iOS etc
fun initKoin() = initKoin{}