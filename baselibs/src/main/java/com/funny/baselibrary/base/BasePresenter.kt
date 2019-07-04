package com.zdtc.ue.school.yw.base

import android.os.Bundle

import java.lang.ref.Reference
import java.lang.ref.WeakReference

/**
 * BasePresenter
 *
 * @author dashixong
 */

open class BasePresenter<V, T>(view: V, provider: T) : LifeCycleListener {

    private var mViewRef: Reference<V>? = null
    private var mView: V? = null
    private var mProviderRef: Reference<T>? = null
    private var mProvider: T? = null//Activity或则Fragment

    /**
     * 获取
     *
     * @return
     */
    val view: V?
        get() = if (mViewRef == null) {
            null
        } else mViewRef!!.get()

    /**
     * 获取
     *
     * @return
     */
    val provider: T?
        get() = if (mProviderRef == null) {
            null
        } else mProviderRef!!.get()

    /**
     * 是否已经关联
     *
     * @return
     */
    val isViewAttached: Boolean
        get() = mViewRef != null && mViewRef!!.get() != null

    /**
     * 是否已经关联
     *
     * @return
     */
    val isProviderAttached: Boolean
        get() = mProviderRef != null && mProviderRef!!.get() != null


    init {
        attachView(view)
        attachProvider(provider)
        setListener(provider)
    }

    /**
     * 设置生命周期监听
     *
     */
    private fun setListener(provider: T) {
        if (provider != null) {
            if (provider is BaseActivity) {
                (provider as BaseActivity).setOnLifeCycleListener(this)
            } else if (provider is BaseFragment) {
                (provider as BaseFragment).setOnLifeCycleListener(this)
            }
        }
    }

    /**
     * 关联
     *
     * @param view
     */
    private fun attachView(view: V) {
        mViewRef = WeakReference(view)
        mView = mViewRef!!.get()
    }

    /**
     * 关联
     *
     * @param provider
     */
    private fun attachProvider(provider: T) {
        mProviderRef = WeakReference(provider)
        mProvider = mProviderRef!!.get()
    }

    /**
     * 销毁
     */
    private fun detachView() {
        if (isViewAttached) {
            mViewRef!!.clear()
            mViewRef = null
        }
    }

    /**
     * 销毁
     */
    private fun detachProvider() {
        if (isProviderAttached) {
            mProviderRef!!.clear()
            mProviderRef = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle) {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {
        detachView()
        detachProvider()
    }
}
