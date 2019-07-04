package com.zdtc.ue.school.yw

import android.content.Context
import android.support.multidex.MultiDex
import com.funny.baselibrary.BaseApp

/**
 * App
 *
 * @author dashixiong
 * @date 2018/5/23 0023
 *
 *
 * No such property: code for class: Script1
 */

class App : BaseApp() {

    companion object {
        @JvmStatic lateinit var instance: App

//        var app: Context by Delegates.notNull()
//            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}
