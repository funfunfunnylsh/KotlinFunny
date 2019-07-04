package com.zdtc.ue.school.yw.util.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.funny.baselibrary.util.imageloader.GlideRoundTransform
import com.zdtc.ue.school.yw.util.FileUtils


object ImageLoader {

    // 加载网络图片
    fun load(context: Context, url: String, imageView: ImageView, placeholder: Int) {
        val options = RequestOptions()
                .placeholder(placeholder)
                .dontAnimate()
                .transform(CircleCrop())
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView)
    }

    // 加载圆型网络图片
    fun loadCircle(context: Context, url: String, imageView: ImageView, placeholder: Int) {
        val options = RequestOptions()
                .placeholder(placeholder)
                .dontAnimate()
                .transform(CircleCrop())

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        imageView.setImageDrawable(resource)
                    }
                })
    }

    // 加载圆角网络图片
    fun loadRound(context: Context, url: String, imageView: ImageView, placeholder: Int) {
        val options = RequestOptions()
                .placeholder(placeholder)
                .transform(GlideRoundTransform())
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context)
                .load(url)
                .apply(options)
                //                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }

    //Glide保存图片
    fun savePicture(context: Context, url: String) {
        Glide.with(context).asBitmap().load(url).into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                FileUtils.saveImage(context, resource, System.currentTimeMillis().toString() + "")
            }
        })
    }


}
