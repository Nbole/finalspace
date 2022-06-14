package com.example.finalspace.android

import android.app.Application
import com.example.finalspace.android.vm.viewModelModule
import com.example.finalspace.di.initKoin
import org.koin.android.ext.koin.androidContext

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            modules(viewModelModule)
            androidContext(this@BaseApp)
        }
    }
}
