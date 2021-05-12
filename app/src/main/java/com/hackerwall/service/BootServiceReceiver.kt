package com.hackerwall.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import com.hackerwall.di.HackerWallApp
import com.hackerwall.di.ServiceLocator

class BootServiceReceiver : BroadcastReceiver() {
    lateinit var serviceLocator: ServiceLocator

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!::serviceLocator.isInitialized) {
            serviceLocator = (context as HackerWallApp).serviceLocator
        }

        when (intent?.action) {
            ACTION_BOOT_COMPLETED -> serviceLocator.providesJobManager().scheduleJobs()
            else -> { }
        }

        serviceLocator.providesJobManager().scheduleJobs()
        // Log open
        serviceLocator.provideLogger().writeDebugLog("*********************************8")
        serviceLocator.provideLogger().writeDebugLog("*********************************8")
        serviceLocator.provideLogger().writeDebugLog("*********************************8")
        serviceLocator.provideLogger().writeDebugLog("*********************************8")
        serviceLocator.provideLogger().writeDebugLog("Boot Service onReceive ${intent?.action}")
        serviceLocator.provideLogger().writeDebugLog("*********************************8")
        serviceLocator.provideLogger().writeDebugLog("*********************************8")
        serviceLocator.provideLogger().writeDebugLog("*********************************8")
        serviceLocator.provideLogger().writeDebugLog("*********************************8")
    }
}