package com.hackerwall.base

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import com.hackerwall.di.HackerWallApp
import com.hackerwall.di.ServiceLocator
import com.hackerwall.images.ESBTransformation

class ImageManager(applicationContext: Context) {
    private val serviceLocator = (applicationContext as HackerWallApp).serviceLocator

    fun getWallpaper(callback: (Drawable) -> Unit) {
        val glide = serviceLocator.providesGlide()
        val deviceInfo = serviceLocator.provideDeviceInfo()
//        val url = "https://home-remote-api.herokuapp.com/freedom-tower.png"
        val url = "https://home-remote-api.herokuapp.com/esb.png"
        //val hourCacheKey = ObjectKey(System.currentTimeMillis() / (60 * 60 * 1000))
        val hourCacheKey = ObjectKey(System.currentTimeMillis())

        glide
            .load(url)
            .transform(ESBTransformation(deviceInfo))
            .signature(hourCacheKey)
            .into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    callback(resource)
                }
            })
    }
}