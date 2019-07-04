package com.zdtc.ue.school.yw.network.function


import com.zdtc.ue.school.yw.network.exception.ServerException
import com.zdtc.ue.school.yw.network.model.HttpResponse
import com.zdtc.ue.school.yw.util.LogUtils
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

/**
 * 服务器结果处理函数
 *
 * @author ZhongDaFeng
 */
class ServerResultFunction<T> : Function<HttpResponse<T>, ObservableSource<T>> {
    override fun apply(@NonNull response: HttpResponse<T>): ObservableSource<T> {
        //打印服务器回传结果
        LogUtils.e(response.toString())
        if (!response.isSuccess) {
            if (response.status == 107) {
                //token过期  这时候用户需要自己重新登录
            }
            return Observable.error(ServerException(response.status, response.msg!!))
        }
        return if (null != response.result) {
            Observable.just(response.result!!)
        } else {
            Observable.just("" as T)
        }
    }
}
