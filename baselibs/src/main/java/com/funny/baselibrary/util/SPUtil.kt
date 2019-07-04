package com.zdtc.ue.school.yw.util

import android.content.Context

/**
 * SharedPreferences的共同类
 *
 * @author Administrator
 */
object SPUtil {

    private val UE_SHAREDPREFERENCES = "UE_2.0.0"

    /**
     * 保存数据到SharedPreferences 返回true表示存数据成功，返回false表示存数据失败
     */
    @Synchronized
    fun saveData(context: Context, key: String,
                 data: Any): Boolean {
        try {
            val preferences = context.getSharedPreferences(
                    UE_SHAREDPREFERENCES, Context.MODE_PRIVATE)
            val type = data.javaClass.simpleName
            val edit = preferences.edit()
            if ("Integer" == type) {
                edit.putInt(key, data as Int)
            } else if ("Boolean" == type) {
                edit.putBoolean(key, data as Boolean)
            } else if ("String" == type) {
                edit.putString(key, data as String)
            } else if ("Float" == type) {
                edit.putFloat(key, data as Float)
            } else if ("Long" == type) {
                edit.putLong(key, data as Long)
            }
            edit.commit()

        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }

    /**
     * 从SharedPreferences获取数据 返回为null读取数据失败
     */
    @Synchronized
    fun getData(context: Context, key: String,
                defValue: Any): Any? {
        try {
            val preferences = context.getSharedPreferences(
                    UE_SHAREDPREFERENCES, Context.MODE_PRIVATE)
            val type = defValue.javaClass.simpleName
            if ("Integer" == type) {
                return preferences.getInt(key, defValue as Int)
            } else if ("Boolean" == type) {
                return preferences.getBoolean(key, defValue as Boolean)
            } else if ("String" == type) {
                return preferences.getString(key, defValue as String)
            } else if ("Float" == type) {
                return preferences.getFloat(key, defValue as Float)
            } else if ("Long" == type) {
                return preferences.getLong(key, defValue as Long)
            }
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 从SharedPreferences移除数据 返回为null读取数据失败
     */
    @Synchronized
    fun removeData(context: Context, key: String) {
        try {
            val preferences = context.getSharedPreferences(UE_SHAREDPREFERENCES, Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.remove(key)
            editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
