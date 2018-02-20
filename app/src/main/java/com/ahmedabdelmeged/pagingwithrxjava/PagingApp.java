package com.ahmedabdelmeged.pagingwithrxjava;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Ahmed Abd-Elmeged on 2/13/2018.
 */
public class PagingApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

}
