package com.hackerwall.jobs

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.hackerwall.R
import com.hackerwall.base.Event
import com.hackerwall.di.HackerWallApp
import org.greenrobot.eventbus.EventBus
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class WallpaperJobService : JobService() {
    companion object {
        const val jobId = 9001
    }

    private val NOTIFICATION_ID = 0
    private val CHANNEL_ID = "main"

    override fun onStartJob(params: JobParameters): Boolean {
        val time  = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"))
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
        EventBus.getDefault().post(Event.WallpaperJobFire("$date $time"))

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