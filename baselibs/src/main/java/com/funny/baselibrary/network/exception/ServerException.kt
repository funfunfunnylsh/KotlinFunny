package com.zdtc.ue.school.yw.network.exception

/**
 * 自定义服务器错误
 *
 * @author DASHIXIONG
 */
class ServerException(val code: Int, val msg: String) : RuntimeException() {

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private fun getExceptionMessage(code: Int): String {
        val message : String
        when (code) {
            ERROR.PHONE_ERROR -> message = "手机号格式不正确"
            ERROR.SMSCODE_OUTOFDATE -> message = "验证码过期"
            ERROR.SMSCODE_ERROR -> message = "验证码错误"
            else -> message = "操作失败"
        }
        return message
    }

    /**
     * 与服务器约定好的异常
     */
    private object ERROR {
        val PHONE_ERROR = 104

        val SMSCODE_OUTOFDATE = 105

        val SMSCODE_ERROR = 106
    }
}
