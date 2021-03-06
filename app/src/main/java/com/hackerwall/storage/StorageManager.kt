package com.hackerwall.storage

import android.content.Context

class StorageManager(applicationContext: Context) {
    var wallpaperJobLastRun by DelegatedSharedPreference(applicationContext, "wallpaperJobLastRun", "")
    var errorLog by DelegatedSharedPreference(applicationContext, "errorLog", "")
    var log by DelegatedSharedPreference(applicationContext, "log", "")
}