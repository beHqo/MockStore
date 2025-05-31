package com.kilid.task

import android.app.Application
import com.kilid.task.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin { androidContext(this@MyApplication) }
    }
}