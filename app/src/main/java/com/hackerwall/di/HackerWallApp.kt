package com.hackerwall.di

import android.app.Application

class HackerWallApp : Application() {

    lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()
        serviceLocator = ServiceLocator(applicationContext)

        // Start jobs.
        serviceLocator.providesJobManager().scheduleJobs()
    }
}