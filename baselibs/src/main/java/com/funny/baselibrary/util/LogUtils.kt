package com.zdtc.ue.school.yw.util

import android.util.Log

/**
 * LOG工具类
 *
 * @author ZhongDaFeng
 */
object LogUtils {

    private val TAG = "Rx-Mvp"
    private var allowD = true
    private var allowE = true
    private var allowI = true
    private var allowV = true
    private var allowW = true

    /**
     * 开启Log
     *
     * @author ZhongDaFeng
     */
    fun openLog(flag: Boolean) {
        allowD = flag
        allowE = flag
        allowI = flag
        allowV = flag
        allowW = flag
    }

    fun d(content: String) {
        if (!allowD)
            return
        Log.d(TAG, content)
    }

    fun e(content: String) {
        if (!allowE)
            return
        Log.e(TAG, content)
    }

    fun i(content: String) {
        if (!allowI)
            return
        Log.i(TAG, content)
    }

    fun v(content: String) {
        if (!allowV)
            return
        Log.v(TAG, content)
    }

    fun w(content: String) {
        if (!allowW)
            return
        Log.w(TAG, content)
    }

}
