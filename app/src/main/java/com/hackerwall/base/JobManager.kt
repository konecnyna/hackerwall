package com.hackerwall.base

import android.R.id
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.PersistableBundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.core.os.persistableBundleOf
import com.hackerwall.jobs.WallpaperJobService
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


class JobManager(private val applicationContext: Context) {
    private val jobScheduler =
        applicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

    fun scheduleJobs() {
        if (nextWallpaperJob() == null) {
            scheduleWallpaperJob()
        }
    }


    fun scheduleWallpaperJob(): Int {
        val interval: Long = TimeUnit.MINUTES.toMillis(15)
        val isPersistent = true
        val networkType = JobInfo.NETWORK_TYPE_ANY

        // The JobService that we want to run
        val name = ComponentName(applicationContext, WallpaperJobService::class.java)

        val time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"))
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
        val bundle = persistableBundleOf(
            "runDate" to "$date $time",
            "id" to "wallpaperjob"
        )

        val jobInfo = JobInfo.Builder(WallpaperJobService.jobId, name)
            .setPeriodic(interval)
            .setExtras(bundle)
            .setRequiredNetworkType(networkType)
            .setPersisted(isPersistent)
            .build()


        // Schedule the job
        return jobScheduler.schedule(jobInfo)
    }

    fun nextWallpaperJob(): JobInfo? {
        return jobScheduler.allPendingJobs.find { it.id == WallpaperJobService.jobId }
    }
}