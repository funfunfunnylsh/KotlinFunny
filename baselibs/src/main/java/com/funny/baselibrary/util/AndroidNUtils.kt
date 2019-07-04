package com.zdtc.ue.school.yw.util

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import java.io.File

/**
 * android 7.0 拍照和裁剪适配工具类
 * Created by admin on 2017/5/19.
 */

object AndroidNUtils {

    /**
     * 在Android 7.0以后，用了Content Uri 替换了原本的File Uri，故在targetSdkVersion=24的时候，部分 “`Uri.fromFile()“` 方法就不适用了。
     * File Uri 对应的是文件本身的存储路径  Content Uri 对应的是文件在Content Provider的路径
     * 所以在android 7.0 以上，我们就需要将File Uri转换为 Content Uri。
     *
     */
    fun getImageContentUri(context: Context, imageFile: File): Uri? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val filePath = imageFile.absolutePath
            val cursor = context.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    arrayOf(MediaStore.Images.Media._ID),
                    MediaStore.Images.Media.DATA + "=? ",
                    arrayOf(filePath), null)

            if (cursor != null && cursor.moveToFirst()) {
                val id = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID))
                val baseUri = Uri.parse("content://media/external/images/media")
                return Uri.withAppendedPath(baseUri, "" + id)
            } else {
                if (imageFile.exists()) {
                    val values = ContentValues()
                    values.put(MediaStore.Images.Media.DATA, filePath)
                    return context.contentResolver.insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                } else {
                    return null
                }
            }
        } else {
            return Uri.fromFile(imageFile)
        }
    }

    /**
     * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
     * 并且这样可以解决MIUI系统上拍照返回size为0的情况
     * getUriForFile()的第二个参数getPackageName() + ".provider"
     * 也就是第一步在AndroidManifest中provider中的属性android:authorities的值，前后两个provider要对应上
     */
    fun getContentUri(context: Context, imageFile: File): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            FileProvider.getUriForFile(context, context.packageName + ".provider", imageFile)
        } else {
            Uri.fromFile(imageFile)

        }
    }


}
