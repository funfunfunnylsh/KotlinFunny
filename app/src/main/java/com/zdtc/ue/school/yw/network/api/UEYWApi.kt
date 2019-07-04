package com.zdtc.ue.school.yw.network.api


import com.zdtc.ue.school.yw.network.model.HttpResponse
import com.zdtc.ue.school.yw.network.model.LoginBean

import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author dashixiong
 */
interface UEYWApi {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("sysUser/login")
    fun login(@FieldMap body: Map<String, @JvmSuppressWildcards Any>): Observable<HttpResponse<LoginBean>>


}
