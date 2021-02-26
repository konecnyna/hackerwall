package com.hackerwall.base

import android.R.id
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import com.hackerwall.jobs.WallpaperJobService
import java.util.concurrent.TimeUnit


class JobManager(private val applicationContext: Context) {
    private val jobScheduler =
        applicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

    fun scheduleJobs() {
        scheduleWallpaperJob()
    }


    fun scheduleWallpaperJob(): Int {
        val interval: Long = TimeUnit.HOURS.toMillis(1)
        val isPersistent = true
        val networkType = JobInfo.NETWORK_TYPE_ANY

        // The JobService that we want to run
        val name = ComponentName(applicationContext, WallpaperJobService::class.java)

        val jobInfo = JobInfo.Builder(WallpaperJobService.jobId, name)
            .setPeriodic(interval)
            .setRequiredNetworkType(networkType)
            .setPersisted(isPersistent)
            .build()


        // Schedule the job
        return jobScheduler.schedule(jobInfo)
    }
}