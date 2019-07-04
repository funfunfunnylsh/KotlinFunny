package com.zdtc.ue.school.yw.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.widget.LinearLayout
import com.funny.baselibrary.BaseApp


/**
 * 屏幕相关的辅助类
 */
object DisplayUtil {


    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * （DisplayMetrics类中属性density）
     * @return
     */
    fun px2dip(pxValue: Float): Int {
        val scale = BaseApp.instance.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * （DisplayMetrics类中属性density）
     * @return
     */
    fun dip2px(dipValue: Float): Int {
        val scale = BaseApp.instance.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun px2sp(pxValue: Float): Int {
        val fontScale = BaseApp.instance.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    fun sp2px(spValue: Float): Int {
        val fontScale = BaseApp.instance.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 直接获取控件的宽、高
     * @param view
     * @return int[]
     */
    fun getWidgetWH(view: View): IntArray {
        val vto2 = view.viewTreeObserver
        vto2.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeGlobalOnLayoutListener(this)
            }
        })
        return intArrayOf(view.width, view.height)
    }

    /**
     * 直接获取控件的宽、高
     * @param view
     * @return int[]
     */
    fun getViewHeight(view: View): Int {
        val vto2 = view.viewTreeObserver
        vto2.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeGlobalOnLayoutListener(this)
            }
        })
        return view.height
    }

    /**
     * 直接获取控件的宽、高
     * @param view
     * @return int[]
     */
    fun getViewWidth(view: View): Int {
        val vto2 = view.viewTreeObserver
        vto2.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeGlobalOnLayoutListener(this)
            }
        })
        return view.width
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    fun getScreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    fun getScreenHeight(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.heightPixels
    }

    /**
     * 获得状态栏的高度
     * 注意：该方法只能在Activity类中使用，在测试模式下失败
     * @param context
     * @return
     */
    fun getStatusBarHeight(context: Context): Int {
        var statusBarHeight = -1
        try {
            val clazz = Class.forName("com.android.internal.R\$dimen")
            val `object` = clazz.newInstance()
            val height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(`object`).toString())
            statusBarHeight = context.resources.getDimensionPixelSize(height)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return statusBarHeight
    }

    /**
     * 获取控件的宽
     * @param view
     * @return
     */
    fun getWidgetWidth(view: View): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)//先度量
        return view.measuredWidth
    }

    /**
     * 获取控件的高
     * @param view
     * @return
     */
    fun getWidgetHeight(view: View): Int {
        val w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(w, h)//先度量
        return view.measuredHeight
    }

    /**
     * 设置控件宽
     * @param view
     * @param width
     */
    fun setWidgetWidth(view: View, width: Int) {
        val params = view.layoutParams as LinearLayout.LayoutParams
        params.width = width
        view.layoutParams = params
    }

    /**
     * 设置控件高
     * @param view
     * @param height
     */
    fun setWidgetHeight(view: View, height: Int) {
        val params = view.layoutParams as LinearLayout.LayoutParams
        params.height = height
        view.layoutParams = params
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    fun setBackgroundAlpha(activity: Activity, bgAlpha: Float) {
        val lp = activity.window.attributes
        lp.alpha = bgAlpha
        activity.window.attributes = lp
    }

    //----------------------------------------------

    /**
     * 获取当前屏幕截图，包含状态栏（这个方法没测试通过）
     *
     * @param activity
     * @return Bitmap
     */
    fun snapShotWithStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        val bp: Bitmap?
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height)
        view.destroyDrawingCache()
        return bp
    }

    /**
     * 获取当前屏幕截图，不包含状态栏（这个方法没测试通过）
     *
     * @param activity
     * @return Bitmap
     */
    fun snapShotWithoutStatusBar(activity: Activity): Bitmap? {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true
        view.buildDrawingCache()
        val bmp = view.drawingCache
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight = frame.top

        val width = getScreenWidth(activity)
        val height = getScreenHeight(activity)
        val bp: Bitmap?
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight)
        view.destroyDrawingCache()
        return bp
    }

}
