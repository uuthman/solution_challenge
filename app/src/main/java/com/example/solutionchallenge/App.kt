package com.example.solutionchallenge

import android.app.Application
import com.example.solutionchallenge.di.apiModule
import com.example.solutionchallenge.di.repositoryModule
import com.example.solutionchallenge.di.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@App)
            modules(
                apiModule,
                userModule,
                repositoryModule
            )
        }
    }
}