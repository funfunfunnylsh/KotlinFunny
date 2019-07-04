package com.zdtc.ue.school.yw.view.iface


import com.zdtc.ue.school.yw.base.IBaseView
import com.zdtc.ue.school.yw.network.model.LoginBean

interface ILoginView : IBaseView {

    //登陆成功
    fun loginSuccess(data: LoginBean)

    fun getSmsCode(isSuccess: Boolean, msg: String)

}
