package com.zdtc.ue.school.yw.base

import android.os.Bundle

/**
 * 生命周期监听
 *
 * @author ZhongDaFeng
 * @date 2017/7/15
 */

interface LifeCycleListener {

    fun onCreate(savedInstanceState: Bundle)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()

}
