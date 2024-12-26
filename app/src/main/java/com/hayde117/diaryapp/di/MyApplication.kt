package com.hayde117.diaryapp.di

//import android.app.Application
//import dagger.hilt.android.HiltAndroidApp
//
//@HiltAndroidApp
//class MyApplication: Application()

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)  // Set application context
            modules(databaseModule, appModule)             // Load Koin modules (you can add more here)
        }
    }
}
