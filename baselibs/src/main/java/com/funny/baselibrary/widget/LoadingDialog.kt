package com.zdtc.ue.school.yw.widget

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.funny.baselibrary.R


/**
 * description:弹窗浮动加载进度条
 * Created by xsf
 * on 2016.07.17:22
 */
object LoadingDialog {
    /** 加载数据对话框  */
    private var mLoadingDialog: Dialog? = null

    /**
     * 显示加载对话框
     * @param context 上下文
     * @param msg 对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    fun showDialogForLoading(context: Context, msg: String, cancelable: Boolean): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        val loadingText = view.findViewById<View>(R.id.id_tv_loading_dialog_text) as TextView
        loadingText.text = msg

        mLoadingDialog = Dialog(context, R.style.CustomProgressDialog)
        mLoadingDialog!!.setCancelable(cancelable)
        mLoadingDialog!!.setCanceledOnTouchOutside(false)
        mLoadingDialog!!.setContentView(view, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
        mLoadingDialog!!.show()
        return mLoadingDialog as Dialog
    }

    fun showDialogForLoading(context: Context): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        val loadingText = view.findViewById<View>(R.id.id_tv_loading_dialog_text) as TextView
        loadingText.text = "请求中..."

        mLoadingDialog = Dialog(context, R.style.CustomProgressDialog)
        mLoadingDialog!!.setCancelable(true)
        mLoadingDialog!!.setCanceledOnTouchOutside(false)
        mLoadingDialog!!.setContentView(view, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT))
        mLoadingDialog!!.show()
        return mLoadingDialog as Dialog
    }

    /**
     * 关闭加载对话框
     */
    fun cancelDialogForLoading() {
        if (mLoadingDialog != null) {
            mLoadingDialog!!.cancel()
            mLoadingDialog = null
        }
    }
}
