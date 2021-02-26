package com.hackerwall.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.hackerwall.base.ImageManager
import com.hackerwall.base.JobManager
import com.hackerwall.models.DeviceInfo
import com.hackerwall.wallpaperservice.WallPaperManagerRepo
import com.hackerwall.wallpaperservice.WallpaperManagerImpl


class ServiceLocator(private val applicationContext: Context) {
    private lateinit var wallpaperManagerImpl: WallPaperManagerRepo
    private lateinit var jobManager: JobManager
    private lateinit var deviceInfo: DeviceInfo
    private lateinit var imageManager: ImageManager

    fun providesGlide(): RequestManager = Glide.with(applicationContext)

    fun providesWallpaperManager(): WallPaperManagerRepo {
        if (!::wallpaperManagerImpl.isInitialized) {
            wallpaperManagerImpl = WallpaperManagerImpl(applicationContext)
        }
        return wallpaperManagerImpl
    }

    fun providesJobManager(): JobManager {
        if (!::jobManager.isInitialized) {
            jobManager = JobManager(applicationContext)
        }
        return jobManager
    }

    fun providesImageManager(): ImageManager {
        if (!::imageManager.isInitialized) {
            imageManager = ImageManager(applicationContext)
        }
        return imageManager
    }


    fun provideDeviceInfo(): DeviceInfo {
        if (!::deviceInfo.isInitialized) {
            val displayMetrics = applicationContext.resources.displayMetrics;
            deviceInfo = DeviceInfo(
                deviceWidth = displayMetrics.widthPixels,
                deviceHeight = displayMetrics.heightPixels
            )
        }

        return deviceInfo
    }
}