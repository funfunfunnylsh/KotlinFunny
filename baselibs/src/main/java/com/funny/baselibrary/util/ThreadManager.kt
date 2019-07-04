package com.zdtc.ue.school.yw.util

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Description: 简易线程池管理工具.
 */

object ThreadManager {
    private var threadPool: ThreadPoolExecutor? = null

    fun execute(runnable: Runnable) {
        if (null == threadPool) {
            threadPool = ThreadPoolExecutor(
                    1, 1, 10, TimeUnit.SECONDS,
                    ArrayBlockingQueue(1),
                    ThreadPoolExecutor.DiscardOldestPolicy())
        }
        threadPool!!.execute(runnable)
    }

    fun shutdown() {
        if (threadPool == null) {
            return
        }
        if (threadPool!!.isShutdown) {
            return
        }
        threadPool!!.shutdownNow()
        threadPool = null
    }
}
