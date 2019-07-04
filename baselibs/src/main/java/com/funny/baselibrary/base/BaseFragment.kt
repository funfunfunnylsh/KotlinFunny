package com.zdtc.ue.school.yw.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.trello.rxlifecycle2.components.RxFragment


/**
 * @author Administrator
 */
abstract class BaseFragment : RxFragment() {

    private var unbinder: Unbinder? = null
    /**
     * 回调函数
     */
    var mListener: LifeCycleListener? = null
    private var rootView: View? = null

    protected abstract fun getContentViewId(): Int


    fun setOnLifeCycleListener(listener: LifeCycleListener) {
        mListener = listener
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null)
            rootView = inflater.inflate(getContentViewId(), container, false)
        unbinder = ButterKnife.bind(this, rootView!!)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    abstract fun initView()

    protected abstract fun initData()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (mListener != null) {
            mListener!!.onCreate(savedInstanceState!!)
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
        intent.setClass(activity, cls)
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
        intent.setClass(activity, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

}
/**
 * 通过Class跳转界面
 */
