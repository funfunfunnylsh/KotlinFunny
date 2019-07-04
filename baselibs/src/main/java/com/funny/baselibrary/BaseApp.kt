package com.funny.baselibrary

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import kotlin.properties.Delegates

/**
 * BaseApp
 */
open class BaseApp : Application() {

    companion object {

        @JvmField
        val TAG = "BaseApp"

        var instance: Context by Delegates.notNull()
            private set

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}