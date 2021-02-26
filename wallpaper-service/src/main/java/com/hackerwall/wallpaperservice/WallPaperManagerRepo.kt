package com.hackerwall.wallpaperservice

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

interface WallPaperManagerRepo {
    fun setWallpaper(bitmap: Bitmap)
    fun setWallpaper(drawable: Drawable)
}