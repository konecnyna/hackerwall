package com.hackerwall.base

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import com.hackerwall.di.HackerWallApp
import com.hackerwall.images.ESBTransformation

class ImageManager(applicationContext: Context) {
    private val serviceLocator = (applicationContext as HackerWallApp).serviceLocator

    fun getWallpaper(forceUpdate: Boolean = false, callback: (Bitmap) -> Unit) {
        val glide = serviceLocator.providesGlide()
        val deviceInfo = serviceLocator.provideDeviceInfo()
//       val url = "https://home-remote-api.herokuapp.com/freedom-tower.png"
        val url = "https://home-remote-api.herokuapp.com/esb.png"

        val hourCacheKey = if (forceUpdate) {
            ObjectKey(System.currentTimeMillis())
        } else {
            ObjectKey(System.currentTimeMillis() / (60 * 60 * 1000))
        }

        glide
            .asBitmap()
            .load(url)
            .transform(ESBTransformation(deviceInfo))
            .signature(hourCacheKey)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }
}