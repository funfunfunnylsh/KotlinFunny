package com.zdtc.ue.school.yw.view.activity


import android.annotation.SuppressLint
import android.provider.Settings
import com.zdtc.ue.school.yw.R
import com.zdtc.ue.school.yw.base.BaseActivity
import com.zdtc.ue.school.yw.network.exception.ApiException
import com.zdtc.ue.school.yw.network.model.LoginBean
import com.zdtc.ue.school.yw.presenter.LoginPresenter
import com.zdtc.ue.school.yw.util.AccountValidatorUtil
import com.zdtc.ue.school.yw.util.SPUtil
import com.zdtc.ue.school.yw.view.iface.ILoginView
import com.zdtc.ue.school.yw.widget.LoadingDialog
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), ILoginView {

    private var loginPresenter: LoginPresenter? = null
    private var phone: String=""
    private var androidIDs: String=""
    private var pass = ""


    override fun getContentViewId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        tv_login.setOnClickListener {
            check()
        }
    }

    @SuppressLint("HardwareIds")
    override fun initData() {
        loginPresenter = LoginPresenter(this, this)
        androidIDs = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)


    }

    @SuppressLint("HardwareIds")
    private fun check() {
        phone = edit_name.text.toString()
        if (phone.isEmpty()) {
            return
        } else if (!AccountValidatorUtil.isMobile(phone)) {
            return
        }
        pass = edit_password.text.toString()
        if (pass.isEmpty()) {
            return
        }
        loginPresenter!!.login(phone, pass, androidIDs)
    }


    override fun onPause() {
        super.onPause()
        LoadingDialog.cancelDialogForLoading()
    }

    override fun loginSuccess(data: LoginBean) {
        // 保存用户登录信息到本地
        SPUtil.saveData(mContext, "phone", phone)
        SPUtil.saveData(mContext, "password", pass)
        finish()
    }

    override fun getSmsCode(isSuccess: Boolean, msg: String) {

    }

    override fun failed(e: ApiException) {

    }

    override fun onBackPressed() {

    }
}