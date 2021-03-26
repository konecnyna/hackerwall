package com.hackerwall.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.hackerwall.base.ImageManager
import com.hackerwall.base.JobManager
import com.hackerwall.models.DeviceInfo
import com.hackerwall.service.EsbCalService
import com.hackerwall.service.Logger
import com.hackerwall.storage.StorageManager
import com.hackerwall.wallpaperservice.WallPaperManagerRepo
import com.hackerwall.wallpaperservice.WallpaperManagerImpl


class ServiceLocator(private val applicationContext: Context) {
    private lateinit var wallpaperManagerImpl: WallPaperManagerRepo
    private lateinit var jobManager: JobManager
    private lateinit var storageManager: StorageManager
    private lateinit var imageManager: ImageManager
    private lateinit var deviceInfo: DeviceInfo
    private lateinit var esbCalService: EsbCalService
    private lateinit var logger: Logger

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

    fun providesEsbCalService(): EsbCalService {
        if (!::esbCalService.isInitialized) {
            esbCalService = EsbCalService()
        }
        return esbCalService
    }

    fun providesStorageManager(): StorageManager {
        if (!::storageManager.isInitialized) {
            storageManager = StorageManager(applicationContext)
        }
        return storageManager
    }

    fun provideLogger(): Logger {
        val storageManager = providesStorageManager()
        if (!::logger.isInitialized) {
            logger = Logger(storageManager)
        }
        return logger
    }
}