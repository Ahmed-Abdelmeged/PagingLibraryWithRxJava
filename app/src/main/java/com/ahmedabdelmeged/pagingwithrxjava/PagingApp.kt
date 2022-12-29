package com.ahmedabdelmeged.pagingwithrxjava

import android.app.Application
import android.content.Context

import timber.log.Timber

/**
 * Created by Ahmed Abd-Elmeged on 2/13/2018.
 */
class PagingApp : Application() {

    companion object {

        lateinit var context: PagingApp

        @JvmStatic
        fun getContextExt(): Context = context.applicationContext

    }

    override fun onCreate() {
        super.onCreate()
        context = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
