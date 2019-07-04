package com.zdtc.ue.school.yw.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import com.zdtc.ue.school.yw.widget.LoadingDialog
import java.io.*
import java.text.DecimalFormat

/**
 * FileUtils
 *
 * @author dashixiong
 *
 * @date 2018/5/31 0031
 *
 * No such property: code for class: Script1
 */
object FileUtils {

    val isSdCardAvailable: Boolean
        get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

    /**
     * 创建根缓存目录
     *
     * @return
     */
    fun createRootPath(context: Context): String {
        var rootPath = ""
        if (isSdCardAvailable) {
            // /sdcard/Android/data/<application package>/
            //            rootPath = context.getExternalCacheDir().getPath().replace("cache", "ueyw");
            rootPath = Environment.getExternalStorageDirectory().toString() + "/ueyw"
        } else {
            // /data/data/<application package>/cache
            rootPath = context.cacheDir.path.replace("cache", "ueyw")
        }
        return rootPath
    }

    /**
     * 递归创建文件夹
     *
     * @param dirPath
     * @return 创建失败返回""
     */
    fun createDir(dirPath: String): String {
        try {
            val file = File(dirPath)
            if (file.parentFile.exists()) {
                LogUtils.i("----- 创建文件夹" + file.absolutePath)
                file.mkdir()
                return file.absolutePath
            } else {
                createDir(file.parentFile.absolutePath)
                LogUtils.i("----- 创建文件夹" + file.absolutePath)
                file.mkdir()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dirPath
    }

    /**
     * 递归创建文件夹
     *
     * @param file
     * @return 创建失败返回""
     */
    fun createFile(file: File): String {
        try {
            if (file.parentFile.exists()) {
                LogUtils.i("----- 创建文件" + file.absolutePath)
                file.createNewFile()
                return file.absolutePath
            } else {
                createDir(file.parentFile.absolutePath)
                file.createNewFile()
                LogUtils.i("----- 创建文件" + file.absolutePath)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun getImageCachePath(path: String): String {
        return createDir(path)
    }

    /**
     * 获取图片缓存目录
     *
     * @return 创建失败, 返回""
     */
    fun getImageCachePath(context: Context): String {
        return createDir(createRootPath(context) + File.separator + "img" + File.separator)
    }

    /**
     * 获取图片裁剪缓存目录
     *
     * @return 创建失败, 返回""
     */
    fun getImageCropCachePath(context: Context): String {
        return createDir(createRootPath(context) + File.separator + "imgCrop" + File.separator)
    }

    /**
     * 将内容写入文件
     *
     * @param filePath eg:/mnt/sdcard/demo.txt
     * @param content  内容
     */
    fun writeFileSdcard(filePath: String, content: String, isAppend: Boolean) {
        try {
            val fout = FileOutputStream(filePath, isAppend)
            val bytes = content.toByteArray()
            fout.write(bytes)
            fout.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * 打开Asset下的文件
     *
     * @param context
     * @param fileName
     * @return
     */
    fun openAssetFile(context: Context, fileName: String): InputStream? {
        val am = context.assets
        var `is`: InputStream? = null
        try {
            `is` = am.open(fileName)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return `is`
    }

    /**
     * 获取Raw下的文件内容
     *
     * @param context
     * @param resId
     * @return 文件内容
     */
    fun getFileFromRaw(context: Context?, resId: Int): String? {
        if (context == null) {
            return null
        }

        val s = StringBuilder()
        try {
            val `in` = InputStreamReader(context.resources.openRawResource(resId))
            val br = BufferedReader(`in`)
            var line: String? = null
            while ((line == br.readLine()) != null) {
                s.append(line)
            }
            return s.toString()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 文件拷贝
     *
     * @param src  源文件
     * @param desc 目的文件
     */
    fun fileChannelCopy(src: File, desc: File) {
        var fi: FileInputStream? = null
        var fo: FileOutputStream? = null
        try {
            fi = FileInputStream(src)
            fo = FileOutputStream(desc)
            val `in` = fi.channel//得到对应的文件通道
            val out = fo.channel//得到对应的文件通道
            `in`.transferTo(0, `in`.size(), out)//连接两个通道，并且从in通道读取，然后写入out通道
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fo?.close()
                fi?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * 转换文件大小
     *
     * @param fileLen 单位B
     * @return
     */
    fun formatFileSizeToString(fileLen: Long): String {
        val df = DecimalFormat("#.00")
        var fileSizeString = ""
        if (fileLen < 1024) {
            fileSizeString = df.format(fileLen.toDouble()) + "B"
        } else if (fileLen < 1048576) {
            fileSizeString = df.format(fileLen.toDouble() / 1024) + "K"
        } else if (fileLen < 1073741824) {
            fileSizeString = df.format(fileLen.toDouble() / 1048576) + "M"
        } else {
            fileSizeString = df.format(fileLen.toDouble() / 1073741824) + "G"
        }
        return fileSizeString
    }

    /**
     * 删除指定文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    fun deleteFile(file: File): Boolean {
        return deleteFileOrDirectory(file)
    }

    /**
     * 删除指定文件，如果是文件夹，则递归删除
     *
     * @param file
     * @return
     * @throws IOException
     */
    fun deleteFileOrDirectory(file: File?): Boolean {
        try {
            if (file != null && file.isFile) {
                return file.delete()
            }
            if (file != null && file.isDirectory) {
                val childFiles = file.listFiles()
                // 删除空文件夹
                if (childFiles == null || childFiles.size == 0) {
                    return file.delete()
                }
                // 递归删除文件夹下的子文件
                for (i in childFiles.indices) {
                    deleteFileOrDirectory(childFiles[i])
                }
                return file.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    /***
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return
     */
    fun getExtensionName(filename: String?): String? {
        if (filename != null && filename.length > 0) {
            val dot = filename.lastIndexOf('.')
            if (dot > -1 && dot < filename.length - 1) {
                return filename.substring(dot + 1)
            }
        }
        return filename
    }

    /**
     * 获取文件内容
     *
     * @param path
     * @return
     */
    fun getFileOutputString(path: String): String? {
        try {
            val bufferedReader = BufferedReader(FileReader(path), 8192)
            val sb = StringBuilder()
            val line: String? = null
            while ((line == bufferedReader.readLine()) != null) {
                sb.append("\n").append(line)
            }
            bufferedReader.close()
            return sb.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun saveImage(context: Context, bmp: Bitmap, bitmapName: String) {
        LoadingDialog.showDialogForLoading(context)
        // 首先保存图片
        val appDir = File(Environment.getExternalStorageDirectory(), "UEYW")
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = "$bitmapName.jpg"
        val file = File(appDir, fileName)
        try {
            val fos = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // 其次把文件插入到系统图库
        //        try {
        //            MediaStore.Images.Media.insertImage(context.getContentResolver(),
        //                    file.getAbsolutePath(), file.getName(), null);
        //        } catch (FileNotFoundException e) {
        //            e.printStackTrace();
        //        }
        // 最后通知图库更新
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val uri = Uri.fromFile(file)
        intent.data = uri
        context.sendBroadcast(intent)

        LoadingDialog.cancelDialogForLoading()
        //        ToastUtils.toast("保存成功,请到相册中查看");
    }
}
