package com.hackerwall.base

sealed class Event {
    data class WallpaperJobFire(val date: String): Event()
}