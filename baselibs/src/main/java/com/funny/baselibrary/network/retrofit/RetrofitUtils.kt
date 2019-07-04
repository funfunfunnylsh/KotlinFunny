package com.zdtc.ue.school.yw.network.retrofit

import com.funny.baselibrary.BaseApp
import com.zdtc.ue.school.yw.base.Constans
import com.zdtc.ue.school.yw.util.LogUtils
import com.zdtc.ue.school.yw.util.NetWorkUtils
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class RetrofitUtils private constructor() {

    private val mInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("Accept", "application/json")
                .method(originalRequest.method(), originalRequest.body())
        val request = requestBuilder.build()
        chain.proceed(request)
    }


    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private val mRewriteCacheControlInterceptor = Interceptor { chain ->
        var request = chain.request()
        if (!NetWorkUtils.isNetConnected(BaseApp.instance)) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
        }
        val originalResponse = chain.proceed(request)
        if (NetWorkUtils.isNetConnected(BaseApp.instance)) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            //                String cacheControl = request.cacheControl().toString();
            originalResponse.newBuilder()
                    //                        .header("Cache-Control", cacheControl)
                    .header("Cache-Control", "public, max-age=$CACHE_CONTROL_AGE")
                    .removeHeader("Pragma")
                    .build()
        } else {
            originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$CACHE_STALE_SEC")
                    .removeHeader("Pragma")
                    .build()
        }
    }

    /**
     * 设置okHttp
     *
     * @author ZhongDaFeng
     */
    private fun okHttpClient(): OkHttpClient {
        //开启Log
        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> LogUtils.e("okHttp:$message") })
        logging.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
                .cache(CACHE)
                .connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
                .addInterceptor(mInterceptor)
                .addInterceptor(mRewriteCacheControlInterceptor)
                .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                .addInterceptor(logging)
                .build()
    }

    /**
     * 获取Retrofit
     *
     */
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(Constans.BASE_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {
        /**
         * 接口地址
         */
        //连接超时时长x秒
        private val CONNECT_TIME_OUT = 30
        //读数据超时时长x秒
        private val READ_TIME_OUT = 30
        //写数据接超时时长x秒
        private val WRITE_TIME_OUT = 30
        private var mInstance: RetrofitUtils? = null

        fun get(): RetrofitUtils {
            if (mInstance == null) {
                synchronized(RetrofitUtils::class.java) {
                    if (mInstance == null) {
                        mInstance = RetrofitUtils()
                    }
                }
            }
            return mInstance!!
        }


        /**
         * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
         * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
         * 设缓存有效期为2天
         */
        private val CACHE_STALE_SEC = (60 * 60 * 24 * 2).toLong()

        /**
         * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
         * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
         */
        private val CACHE_CONTROL_AGE = "60 * 60"

        private val CACHE = Cache(File(BaseApp.instance.cacheDir, "dfs_cache"),
                (1024 * 1024 * 100).toLong())
    }
}
