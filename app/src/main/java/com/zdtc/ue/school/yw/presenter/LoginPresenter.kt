package com.zdtc.ue.school.yw.presenter

import com.zdtc.ue.school.yw.base.BasePresenter
import com.zdtc.ue.school.yw.network.api.ApiUtils
import com.zdtc.ue.school.yw.network.exception.ApiException
import com.zdtc.ue.school.yw.network.model.LoginBean
import com.zdtc.ue.school.yw.network.rx.RxObservable
import com.zdtc.ue.school.yw.network.rx.RxObserver
import com.zdtc.ue.school.yw.view.activity.LoginActivity
import com.zdtc.ue.school.yw.view.iface.ILoginView
import java.util.*

class LoginPresenter(view: ILoginView, provider: LoginActivity) : BasePresenter<ILoginView, LoginActivity>(view, provider) {

    fun login(tel: String, password: String, deviceName: String) {
        val maps = HashMap<String, Any>()
        maps["tel"] = tel
        maps["password"] = password
        maps["deviceName"] = deviceName

        val observer = object : RxObserver<LoginBean>(provider!!) {
            override fun _onError(e: ApiException) {
                view!!.failed(e)
            }

            override fun _onNext(t: LoginBean) {
                view!!.loginSuccess(t)
            }
        }

        val observable = RxObservable.getObservable(ApiUtils.apiService.login(maps), provider!!)
        observable.subscribe(observer)
    }
}
