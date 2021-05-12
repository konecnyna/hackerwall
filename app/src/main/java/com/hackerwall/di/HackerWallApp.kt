package com.hackerwall.di

import android.app.Application
import org.greenrobot.eventbus.EventBus

class HackerWallApp : Application() {

    lateinit var serviceLocator: ServiceLocator

    override fun onCreate() {
        super.onCreate()


        EventBus.builder().installDefaultEventBus()

        serviceLocator = ServiceLocator(applicationContext)

        // Start jobs.
        serviceLocator.providesJobManager().scheduleJobs()

        // Log open
//        serviceLocator.provideLogger().writeDebugLog("Application created.")
    }
}