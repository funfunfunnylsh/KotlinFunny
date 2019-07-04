package com.zdtc.ue.school.yw.network.api


import android.util.Log
import com.zdtc.ue.school.yw.network.retrofit.RetrofitUtils

/**
 * 接口工具类
 *
 */

object ApiUtils {

    val apiService: UEYWApi by lazy {
        Log.d("Api", "apiservice create lazy")
        RetrofitUtils.get().retrofit().create(UEYWApi::class.java)
    }

}
