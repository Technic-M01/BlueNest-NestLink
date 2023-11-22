package com.bytebloomlabs.nestlink

import android.app.Application

class AmplifyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // initialize Amplify when application is starting
        Backend.initialize(applicationContext)
    }
}