package com.zdtc.ue.school.yw.util

import java.util.regex.Pattern

/**
 * 账户相关属性验证工具
 * Created by admin on 2018/1/23.
 */

object AccountValidatorUtil {
    /**
     * 正则表达式：验证用户名
     */
    val REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$"

    /**
     * 正则表达式：验证密码
     */
    val REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$"

    /**
     * 正则表达式：验证手机号
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
    val REGEX_MOBILE = "[1][3456789]\\d{9}"

    /**
     * 正则表达式：验证邮箱
     */
    val REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"

    /**
     * 正则表达式：验证汉字
     */
    val REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$"

    /**
     * 正则表达式：验证身份证
     */
    val REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)"


    /**
     * 正则表达式：验证IP地址
     */
    val REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)"


    /**
     * 正则表达式：验证NAC地址
     */
    private val REGEX_MAC_ADDRESS = "([A-Fa-f0-9]{2}:){5}[A-Fa-f0-9]{2}"

    fun isMac(mac: String): Boolean {
        // 这是真正的MAV地址；正则表达式；
        return if (mac.matches(REGEX_MAC_ADDRESS.toRegex())) {
            true
        } else {
            false
        }
    }


    fun isNumber(str: String): Boolean {
        return Pattern.matches("[0-9]*", str)
    }

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    fun isUsername(username: String): Boolean {
        return Pattern.matches(REGEX_USERNAME, username)
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    fun isPassword(password: String): Boolean {
        return Pattern.matches(REGEX_PASSWORD, password)
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    fun isMobile(mobile: String): Boolean {
        return Pattern.matches(REGEX_MOBILE, mobile)
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    fun isEmail(email: String): Boolean {
        return Pattern.matches(REGEX_EMAIL, email)
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    fun isChinese(chinese: String): Boolean {
        return Pattern.matches(REGEX_CHINESE, chinese)
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    fun isIDCard(idCard: String): Boolean {
        return Pattern.matches(REGEX_ID_CARD, idCard)
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    fun isIPAddr(ipAddr: String): Boolean {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr)
    }
}
