package com.ahmedabdelmeged.pagingwithrxjava

import android.app.Application

import timber.log.Timber

/**
 * Created by Ahmed Abd-Elmeged on 2/13/2018.
 */
class PagingApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
