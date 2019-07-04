package com.funny.baselibrary.widget

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.funny.baselibrary.R
import com.zdtc.ue.school.yw.base.AppManager
import com.zdtc.ue.school.yw.util.DisplayUtil
import kotlinx.android.synthetic.main.titlebar_normal.view.*

class NormalTitleBar : RelativeLayout {

    var mContext: Context

    constructor(context: Context) : super(context, null) {
        this.mContext = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.mContext = context
        View.inflate(context, R.layout.titlebar_normal, this)
        tv_back.setOnClickListener { AppManager.appManager.finishActivity() }
        setLeftImagSrc(R.drawable.app_back)

        //setHeaderHeight();
    }

    fun setHeaderHeight() {
        common_title.setPadding(0, DisplayUtil.getStatusBarHeight(context!!), 0, 0)
        common_title.requestLayout()
    }

    /**
     * 管理返回按钮
     */
    fun setBackVisibility(visible: Boolean) {
        if (visible) {
            tv_back.visibility = View.VISIBLE
        } else {
            tv_back.visibility = View.GONE
        }
    }

    /**
     * 设置标题栏左侧字符串
     *
     * @param visiable
     */
    fun setTvLeftVisiable(visiable: Boolean) {
        if (visiable) {
            tv_back.visibility = View.VISIBLE
        } else {
            tv_back.visibility = View.GONE
        }
    }

    /**
     * 设置标题栏左侧字符串
     *
     * @param tvLeftText
     */
    fun setTvLeft(tvLeftText: String) {
        tv_back.text = tvLeftText
    }


    /**
     * 管理标题
     */
    fun setTitleVisibility(visible: Boolean) {
        if (visible) {
            tv_title.visibility = View.VISIBLE
        } else {
            tv_title.visibility = View.GONE
        }
    }

    fun setTitleText(string: String) {
        tv_title.text = string
    }

    fun setTitleText(string: Int) {
        tv_title.setText(string)
    }

    fun setTitleColor(color: Int) {
        tv_title.setTextColor(color)
    }

    /**
     * 右图标
     */
    fun setRightImagVisibility(visible: Boolean) {
        image_right.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setRightImagSrc(id: Int) {
        image_right.visibility = View.VISIBLE
        image_right.setImageResource(id)
    }

    /**
     * 左图标
     *
     * @param id
     */
    fun setLeftImagSrc(id: Int) {
        val drawable = ContextCompat.getDrawable(mContext, id)
        drawable!!.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        tv_back.setCompoundDrawables(drawable, null, null, null)
    }

    /**
     * 左文字
     *
     * @param str
     */
    fun setLeftTitle(str: String) {
        tv_back.text = str
    }

    /**
     * 右标题
     */
    fun setRightTitleVisibility(visible: Boolean) {
        tv_right.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setRightTitle(text: String) {
        tv_right.text = text
    }

    /*
     * 点击事件
     */
    fun setOnBackListener(listener: View.OnClickListener) {
        tv_back.setOnClickListener(listener)
    }

    fun setOnRightImagListener(listener: View.OnClickListener) {
        image_right.setOnClickListener(listener)
    }

    fun setOnRightTextListener(listener: View.OnClickListener) {
        tv_right.setOnClickListener(listener)
    }

    /**
     * 标题背景颜色
     *
     * @param color
     */
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setBackGroundColor(color: Int) {
        common_title.setBackgroundColor(color)
        common_title.elevation = 0f
    }

}
