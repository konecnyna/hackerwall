package com.hackerwall.wallpaperservice

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

class WallpaperManagerImpl(private val applicationContext: Context) : WallPaperManagerRepo {
    override fun setWallpaper(bitmap: Bitmap) {
        WallpaperManager.getInstance(applicationContext).setBitmap(bitmap)
    }

    override fun setWallpaper(drawable: Drawable) {
        val src = (drawable as BitmapDrawable).bitmap
        WallpaperManager.getInstance(applicationContext).setBitmap(src)
    }
}