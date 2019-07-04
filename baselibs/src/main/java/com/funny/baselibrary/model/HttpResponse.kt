package com.zdtc.ue.school.yw.network.model

import com.google.gson.annotations.SerializedName

class HttpResponse<T> {
    /**
     * 描述信息
     */
    @SerializedName("msg")
    var msg: String? = null

    /**
     * 状态码
     */
    @SerializedName("code")
    var status: Int = 0

    /**
     * 数据对象[成功返回对象,失败返回错误说明]
     */
    @SerializedName("data")
    var result: T? = null

    /**
     * 是否成功(这里约定0)
     *
     * @return
     */
    val isSuccess: Boolean
        get() = status == 0

    override fun toString(): String {
        return "HttpResponse{" +
                "msg='" + msg + '\''.toString() +
                ", status=" + status +
                ", result=" + result +
                '}'.toString()
    }
}
