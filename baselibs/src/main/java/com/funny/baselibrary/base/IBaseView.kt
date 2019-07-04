package com.zdtc.ue.school.yw.base

import com.zdtc.ue.school.yw.network.exception.ApiException

/**
 * IBaseView
 *
 * @author dashixiong
 */

interface IBaseView {
    fun failed(e: ApiException)
}
