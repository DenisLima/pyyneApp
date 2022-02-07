package com.djv.pyyneaplication

import android.app.Application
import com.djv.data.di.dataModule
import com.djv.domain.di.domainModule
import com.djv.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PyyneAplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PyyneAplication)
            modules(listOf(
                dataModule,
                domainModule,
                presentationModule
            ))
        }
    }
}