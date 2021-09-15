package com.example.storagetask

import android.app.Application
import com.example.storagetask.container.AppContainer

class ExampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContainer.initialize(applicationContext)
    }
}
