package com.fdlr.timer

import android.app.Application
import android.content.Context
import com.fdlr.timer.di.repositoryModule
import com.fdlr.timer.di.viewModelModule
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TimerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initiateKoin()
        setupHawk()
        instance = applicationContext
    }

    private fun initiateKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@TimerApplication)
            androidFileProperties()
            modules(viewModelModule, repositoryModule)
        }
    }

    private fun setupHawk() = Hawk.init(this).build()

    companion object {
        lateinit var instance: Context
    }
}