package com.zdtc.ue.school.yw.network.function

import com.zdtc.ue.school.yw.network.exception.ExceptionEngine
import com.zdtc.ue.school.yw.util.LogUtils

import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function

/**
 * http结果处理函数
 *
 */
class HttpResultFunction<T> : Function<Throwable, Observable<T>> {
    override fun apply(@NonNull throwable: Throwable): Observable<T> {
        //打印具体错误
        LogUtils.e("HttpResultFunction:$throwable")
        return Observable.error(ExceptionEngine.handleException(throwable))
    }
}
