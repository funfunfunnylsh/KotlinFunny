package com.zdtc.ue.school.yw.util


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.util.Base64
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

object BMapUtil {
    private val EARTH_RADIUS = 6378137.0

    /**
     * 将View转换为Bitmap
     *
     * @param view
     * @return
     */
    fun getBitmapFromView(view: View): Bitmap {

        view.destroyDrawingCache() // 清理cache

        view.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

        view.layout(view.left, view.top, view.right, view.bottom)
        view.isDrawingCacheEnabled = true
        return view.getDrawingCache(true)
    }

    /**
     * 根据base64字符串获取Bitmap位图 getBitmap
     *
     * @throws
     */
    fun getBitmap(imgBase64Str: String): Bitmap? {
        try {
            val bitmapArray: ByteArray
            bitmapArray = Base64.decode(imgBase64Str, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.size)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    private fun rad(d: Double): Double {
        return d * Math.PI / 180.0
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    fun GetDistance(lng1: Double, lat1: Double, lng2: Double,
                    lat2: Double): Double {
        val radLat1 = rad(lat1)
        val radLat2 = rad(lat2)
        val a = radLat1 - radLat2
        val b = rad(lng1) - rad(lng2)
        var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2.0) + (Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2.0))))
        s = s * EARTH_RADIUS
        s = (Math.round(s * 10000) / 10000).toDouble()
        return s
    }


    /**
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    fun rotaingImageView(angle: Int, bitmap: Bitmap): Bitmap {
        //旋转图片 动作
        val matrix = Matrix()
        matrix.postRotate(angle.toFloat())
        println("angle2=$angle")
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.width, bitmap.height, matrix, true)
    }


    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    fun readPictureDegree(path: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path)
            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return degree
    }

    /**
     * 多张压缩并旋转图片方向
     * @param nativePaths
     * @return
     */
    fun compressAndRotate(nativePaths: List<String>,context :Context): List<String> {
        val paths = ArrayList<String>()
        for (i in nativePaths.indices) {
            val filePath = nativePaths[i]
            try {
                val imageFile = File(filePath)
                // 图片压缩,
                val split = imageFile.absolutePath.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val filename = split[split.size - 1]
                val newFileName = FileUtils.createRootPath(context) + "/small_" + filename
                val newFile = File(newFileName)
                FileUtils.createFile(newFile)
                var bitmap = BitmapCompressor.compressBitmapFromFilePath(imageFile.absolutePath)
                /**
                 * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
                 */
                val degree = BMapUtil.readPictureDegree(imageFile.absolutePath)
                /**
                 * 把图片旋转为正的方向
                 */
                bitmap = BMapUtil.rotaingImageView(degree, bitmap)
                //写入文件
                val compress = bitmap.compress(Bitmap.CompressFormat.JPEG, 85, FileOutputStream(newFile))
                if (compress) {
                    paths.add(newFileName)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return paths
    }

    /**
     * 单张压缩并旋转图片方向
     * @return
     */
    fun compressAndRotate(filepath: String,context :Context): String? {
        try {
            val imageFile = File(filepath)
            // 图片压缩,
            val split = imageFile.absolutePath.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val filename = split[split.size - 1]
            val newFileName = FileUtils.createRootPath(context) + "/small_" + filename
            val newFile = File(newFileName)
            FileUtils.createFile(newFile)
            var bitmap = BitmapCompressor.compressBitmapFromFilePath(imageFile.absolutePath)
            /**
             * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
             */
            val degree = BMapUtil.readPictureDegree(imageFile.absolutePath)
            /**
             * 把图片旋转为正的方向
             */
            bitmap = BMapUtil.rotaingImageView(degree, bitmap)
            //写入文件
            val compress = bitmap.compress(Bitmap.CompressFormat.JPEG, 85, FileOutputStream(newFile))
            if (compress) {
                return newFileName
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}
