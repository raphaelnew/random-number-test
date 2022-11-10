package com.assignment.rnt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * An application with @HiltAndroidApp that triggers Hilt's code generation and
 * adds an application-level dependency container.
 */
@HiltAndroidApp
class RandomNumberApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}