package com.zdtc.ue.school.yw.network.rx


import android.content.Context
import com.funny.baselibrary.BaseApp
import com.funny.baselibrary.R
import com.zdtc.ue.school.yw.network.exception.ApiException
import com.zdtc.ue.school.yw.network.exception.ExceptionEngine
import com.zdtc.ue.school.yw.widget.LoadingDialog
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable


/**
 * 适用Retrofit网络请求Observer(监听者)
 * 备注:
 * 1.重写onSubscribe，添加请求标识
 * 2.重写onError，封装错误/异常处理，移除请求
 * 3.重写onNext，移除请求
 */
abstract class RxObserver<T> : Observer<T> {

    protected var disposable: Disposable? = null
    private var mContext: Context? = null
    private var msg: String? = null
    private var showDialog = true

    constructor(context: Context, msg: String, showDialog: Boolean) {
        this.mContext = context
        this.msg = msg
        this.showDialog = showDialog
    }

    constructor(context: Context) {
        this.mContext = context
        this.msg = BaseApp.instance.getString(R.string.loading)
        this.showDialog = true
    }

    constructor(context: Context, showDialog: Boolean) {
        this.mContext = context
        this.msg = BaseApp.instance.getString(R.string.loading)
        this.showDialog = showDialog
    }

    override fun onError(e: Throwable) {
        if (showDialog) {
            LoadingDialog.cancelDialogForLoading()
        }
        if (e is ApiException) {
            _onError(e)
        } else {
            _onError(ApiException(e, ExceptionEngine.UN_KNOWN_ERROR))
        }
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

    override fun onComplete() {
        if (showDialog) {
            LoadingDialog.cancelDialogForLoading()
        }
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

    override fun onNext(@NonNull t: T) {
        _onNext(t)
    }

    override fun onSubscribe(@NonNull d: Disposable) {
        if (showDialog) {
            try {
                LoadingDialog.showDialogForLoading(mContext!!, msg!!, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        disposable = d
    }

    /**
     * 错误/异常回调
     *
     * @author ZhongDaFeng
     */
    protected abstract fun _onError(e: ApiException)

    /**
     * 成功回调
     *
     * @author ZhongDaFeng
     */
    protected abstract fun _onNext(t: T)

}
