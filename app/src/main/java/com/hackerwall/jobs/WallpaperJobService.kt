package com.hackerwall.jobs

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.app.NotificationCompat
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.signature.ObjectKey
import com.hackerwall.R
import com.hackerwall.di.HackerWallApp
import com.hackerwall.di.ServiceLocator
import com.hackerwall.images.ESBTransformation


class WallpaperJobService : JobService() {
    companion object {
        const val jobId = 9001
    }

    private val NOTIFICATION_ID = 0
    private val CHANNEL_ID = "main"

    override fun onStartJob(params: JobParameters): Boolean {
        Log.d("test1234", "Running wallpaper job.")

        showNotification()
        work()

        return true
    }


    override fun onStopJob(params: JobParameters): Boolean {
        return true
    }

    private fun work() {
        val serviceLocator = (applicationContext as HackerWallApp).serviceLocator
        val wallpaperManager = serviceLocator.providesWallpaperManager()
        val imageManager = serviceLocator.providesImageManager()
        imageManager.getWallpaper {
            wallpaperManager.setWallpaper(it)
        }

    }

    private fun showNotification() {
        val context = baseContext
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mChannel = NotificationChannel(
            CHANNEL_ID,
            "wallpaper",
            NotificationManager.IMPORTANCE_NONE
        )
        notificationManager.createNotificationChannel(mChannel)


        val pi = PendingIntent.getActivity(context, -1, Intent(), PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        ).setContentIntent(pi)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("title")
            .setContentText("content msg")
            .setDefaults(Notification.DEFAULT_VIBRATE)

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

}