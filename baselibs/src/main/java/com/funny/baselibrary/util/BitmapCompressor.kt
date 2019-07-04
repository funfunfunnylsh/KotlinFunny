package com.zdtc.ue.school.yw.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix

import java.io.ByteArrayOutputStream

/**
 * 图片压缩
 * @author Administrator
 */
object BitmapCompressor {


    // 按图片最大宽度压缩图片
    private val OSS_MAX_WIDTH = 1080

    /**
     * 按指定宽高压缩图片
     * 对Bitmap形式的图片进行压缩, 也就是通过设置采样率, 减少Bitmap的像素, 从而减少了它所占用的内存
     * @param filepath
     * @param reqWidth
     * 最大宽度
     * @return
     */
    fun compressBitmapFromFilePath(filepath: String, reqWidth: Int): Bitmap {

        // 原始图片的读取参数
        val oldoptions = BitmapFactory.Options()
        //不为bitmap分配内存空间，只记录一些该图片的信息
        oldoptions.inJustDecodeBounds = true
        // 通过创建图片的方式，取得oldoptions的内容
        BitmapFactory.decodeFile(filepath, oldoptions)
        //源图片的宽度
        var sacle = oldoptions.outWidth / reqWidth
        if (sacle == 0) {
            sacle = 1
        }
        //inSampleSize只能是2的整数次幂，如果不是的话，向下取得最大的2的整数次幂
        oldoptions.inSampleSize = sacle
        // 读取图片
        oldoptions.inJustDecodeBounds = false
        // 图片不抖动
        oldoptions.inDither = false
        // 当系统内存不够时候图片自动被回收  同时设置才会有效
        oldoptions.inPurgeable = true
        oldoptions.inInputShareable = true
        // 读取原始图片    得到的较大的缩略图
        val oldBitmap = BitmapFactory.decodeFile(filepath, oldoptions)
        // 如果图片等于要求的尺寸  或则如果inSampleSize是2的倍数，也就说这个图片已经是我们想要的缩略图了，直接返回即可。
        if (oldBitmap.width == reqWidth || oldBitmap.width < reqWidth) {
            return oldBitmap
        }
        // 计算缩放比例
        val dx = reqWidth / oldBitmap.width.toFloat()
        // 声明一个变化矩阵
        val matrix = Matrix()
        matrix.postScale(dx, dx)
        // 产生一张新图片  把我们之前得到的较大的缩略图进行缩小，让其完全符合实际显示的大小。
        val newBitmap = Bitmap.createBitmap(oldBitmap, 0, 0, oldBitmap.width, oldBitmap.height, matrix, true)
        //如果没有缩放，那么不回收
        if (oldBitmap != newBitmap) {
            //释放Bitmap的native像素数组
            oldBitmap.recycle()
        }
        // 返回新图片
        return newBitmap
    }

    /**
     * 质量压缩,减小大小
     *
     * @author ping 2015-1-5 下午1:29:58
     * @param image
     * @param w
     * @param h
     * @return
     */
    fun compressBitmapToSize(image: Bitmap, w: Float, h: Float): Bitmap {

        val matrix = Matrix()
        matrix.setScale(w / image.width, h / image.height)


        return Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)
    }


    /**
     * 质量压缩,减小大小   等比例
     *
     * @author ping 2015-1-5 下午1:29:58
     * @param image
     * @param w
     * @return
     */
    fun compressBitmapToSize(image: Bitmap, w: Float): Bitmap {

        val matrix = Matrix()
        matrix.setScale(w / image.width, w / image.width)

        return Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)
    }

    fun compressBitmapFromFilePath(filepath: String): Bitmap {

        // 原始图片的读取参数
        val oldoptions = BitmapFactory.Options()
        oldoptions.inJustDecodeBounds = true// 不解析图片,只获取文件头
        BitmapFactory.decodeFile(filepath, oldoptions)
        var sacle = oldoptions.outWidth / OSS_MAX_WIDTH
        if (sacle == 0) {
            sacle = 1
        }
        oldoptions.inSampleSize = sacle
        // 读取图片
        oldoptions.inJustDecodeBounds = false
        // 图片不抖动
        oldoptions.inDither = false
        // 图片允许回收
        oldoptions.inPurgeable = true
        oldoptions.inInputShareable = true
        // 读取原始图片
        val oldBitmap = BitmapFactory.decodeFile(filepath, oldoptions)
        val size = oldBitmap.rowBytes * oldBitmap.height
        // 如果图片等于要求的尺寸
        if (oldBitmap.width <= OSS_MAX_WIDTH) {
            return oldBitmap
        }
        // 计算缩放比例
        val dx = OSS_MAX_WIDTH / oldBitmap.width.toFloat()
        // 声明一个变化矩阵
        val matrix = Matrix()
        matrix.postScale(dx, dx)
        // 产生一张新图片
        val newBitmap = Bitmap.createBitmap(oldBitmap, 0, 0, oldBitmap.width, oldBitmap.height, matrix, true)
        // 返回新图片
        val newsize = newBitmap.rowBytes * newBitmap.height
        return newBitmap
    }

    /**
     * 质量压缩,减小大小
     *
     * @param image
     * @param size
     * @return
     */
    fun compressBitmapToSizes(image: Bitmap, size: Int): Bitmap {
        val out = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 85, out)
        val zoom = Math.sqrt((size * 1024 / out.toByteArray().size.toFloat()).toDouble()).toFloat()

        val matrix = Matrix()
        matrix.setScale(zoom, zoom)

        var result = Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)

        out.reset()
        result.compress(Bitmap.CompressFormat.JPEG, 85, out)
        while (out.toByteArray().size > size * 1024) {
            println(out.toByteArray().size)
            matrix.setScale(0.9f, 0.9f)
            result = Bitmap.createBitmap(result, 0, 0, result.width, result.height, matrix, true)
            out.reset()
            result.compress(Bitmap.CompressFormat.JPEG, 85, out)
        }

        return result
    }

}
