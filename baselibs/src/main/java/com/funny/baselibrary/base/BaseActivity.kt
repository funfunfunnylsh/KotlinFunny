package com.zdtc.ue.school.yw.base

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import butterknife.ButterKnife
import butterknife.Unbinder
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity


/**
 * 基类Activity
 * 备注:所有的Activity都继承自此Activity
 * 1.规范团队开发
 * 2.统一处理Activity所需配置,初始化
 */
abstract class BaseActivity : RxAppCompatActivity() {

    lateinit var mContext: Context
    private var unbinder: Unbinder? = null

    /**
     * 回调函数
     */
    var mListener: LifeCycleListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }

        // 把actvity放到application栈中管理
        AppManager.appManager.addActivity(this)
        setContentView(getContentViewId())
        unbinder = ButterKnife.bind(this)
        mContext = this

        initView()
        initData()

        if (mListener != null && savedInstanceState!= null ) {
            mListener!!.onCreate(savedInstanceState)
        }
    }

    override fun onStart() {
        super.onStart()
        if (mListener != null) {
            mListener!!.onStart()
        }
    }

    override fun onResume() {
        super.onResume()
        if (mListener != null) {
            mListener!!.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mListener != null) {
            mListener!!.onPause()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mListener != null) {
            mListener!!.onStop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mListener != null) {
            mListener!!.onDestroy()
        }
        if (unbinder != null) {
            unbinder!!.unbind()
        }

        AppManager.appManager.finishActivity(this)
    }

    /**
     * 获取显示view的xml文件ID
     */
    protected abstract fun getContentViewId():Int
    /**
     * 初始化应用程序，设置一些初始化数据,获取数据等操作
     */
    protected abstract fun initView()

    protected abstract fun initData()

    fun setOnLifeCycleListener(listener: LifeCycleListener) {
        mListener = listener
    }

    /**
     * 获取权限处理类
     */
    protected val rxPermissions: RxPermissions by lazy {
        RxPermissions(this)
    }


    /**
     * 通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        startActivityForResult(cls, null, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, bundle: Bundle?,
                               requestCode: Int) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    fun startActivity(cls: Class<*>, bundle: Bundle? = null) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

}

