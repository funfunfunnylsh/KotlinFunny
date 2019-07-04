package com.zdtc.ue.school.yw.base

import android.app.Activity
import android.app.ActivityManager
import android.app.NotificationManager
import android.content.Context
import java.util.*

/**
 * activity管理
 */
class AppManager private constructor() {

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack()
        }
        activityStack!!.add(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        try {
            return activityStack!!.lastElement()
        } catch (e: Exception) {
            //            e.printStackTrace();
            return null
        }

    }

    /**
     * 获取当前Activity的前一个Activity
     */
    fun preActivity(): Activity? {
        val index = activityStack!!.size - 2
        return if (index < 0) {
            null
        } else activityStack!![index]
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = activityStack!!.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        if (activity != null) {
            activityStack!!.remove(activity)
            activity.finish()
        }
    }

    /**
     * 移除指定的Activity
     */
    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            activityStack!!.remove(activity)
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        try {
            for (activity in activityStack!!) {
                if (activity.javaClass == cls) {
                    finishActivity(activity)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                activityStack!![i].finish()
            }
            i++
        }
        activityStack!!.clear()
    }

    /**
     * 返回到指定的activity
     *
     * @param cls
     */
    fun returnToActivity(cls: Class<*>) {
        while (activityStack!!.size != 0) {
            if (activityStack!!.peek().javaClass == cls) {
                break
            } else {
                finishActivity(activityStack!!.peek())
            }
        }
    }


    /**
     * 是否已经打开指定的activity
     * @param cls
     * @return
     */
    fun isOpenActivity(cls: Class<*>): Boolean {
        if (activityStack != null) {
            var i = 0
            val size = activityStack!!.size
            while (i < size) {
                if (cls == activityStack!!.peek().javaClass) {
                    return true
                }
                i++
            }
        }
        return false
    }


    /**
     * 退出应用程序
     *
     * @param context
     */
    fun exitApp(context: Context) {
        try {
            finishAllActivity()
            val activityManager = context
                    .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityManager.restartPackage(context.packageName)
            //清除通知栏
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancelAll()
            android.os.Process.killProcess(android.os.Process.myPid())
        } catch (e: Exception) {
        }

    }

    companion object {
        private var activityStack: Stack<Activity>? = null
        @Volatile
        private var instance: AppManager? = null
        /**
         * 单一实例
         */
        val appManager: AppManager
            get() {
                if (instance == null) {
                    synchronized(AppManager::class.java) {
                        if (instance == null) {
                            instance = AppManager()
                            activityStack = Stack()
                        }
                    }

                }
                return instance!!
            }
    }
}