package com.zdtc.ue.school.yw.network.exception

import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException


/**
 * 错误/异常处理工具
 *
 * @author ZhongDaFeng
 */
object ExceptionEngine {
    //未知错误
    val UN_KNOWN_ERROR = 1000
    //解析(服务器)数据错误
    val ANALYTIC_SERVER_DATA_ERROR = 1001
    //解析(客户端)数据错误
    val ANALYTIC_CLIENT_DATA_ERROR = 1002
    //网络连接错误
    val CONNECT_ERROR = 1003
    //网络连接超时
    val TIME_OUT_ERROR = 1004

    fun handleException(e: Throwable): ApiException {
        val ex: ApiException
        //HTTP错误
        if (e is HttpException) {
            ex = ApiException(e, e.code())
            //均视为网络错误
            ex.msg = "网络错误"
            return ex
            //服务器返回的错误
        } else if (e is ServerException) {
            ex = ApiException(e, e.code)
            ex.msg = e.msg
            return ex
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException || e is MalformedJsonException) {
            //解析数据错误
            ex = ApiException(e, ANALYTIC_SERVER_DATA_ERROR)
            ex.msg = "解析错误"
            return ex
        } else if (e is ConnectException) {
            //连接网络错误
            ex = ApiException(e, CONNECT_ERROR)
            ex.msg = "连接失败"
            return ex
        } else if (e is SocketTimeoutException) {
            //网络超时
            ex = ApiException(e, TIME_OUT_ERROR)
            ex.msg = "网络超时"
            return ex
        } else {  //未知错误
            ex = ApiException(e, UN_KNOWN_ERROR)
            ex.msg = "未知错误"
            return ex
        }
    }

}
