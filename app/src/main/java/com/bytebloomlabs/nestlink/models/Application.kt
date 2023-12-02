package com.bytebloomlabs.nestlink.models

import android.app.Application
import com.bytebloomlabs.nestlink.models.Backend

class AmplifyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // initialize Amplify when application is starting
        Backend.initialize(applicationContext)
    }
}