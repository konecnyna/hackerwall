package com.hackerwall.service

import com.hackerwall.storage.StorageManager
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class Logger(private val storageManager: StorageManager) {
    enum class LogLevel(val symbol: String) {
        Debug("🐛"),
        Error("❌")
    }

    fun writeDebugLog(message: String) = logWithDate(LogLevel.Debug, message)
    fun writeErrorLog(message: String) = logWithDate(LogLevel.Error, message)
    suspend fun getLogs(): List<String> = storageManager.log.split("\n").map { it.trim() }.reversed()
    fun clearLogs() { storageManager.log = "" }

    private fun logWithDate(level: LogLevel, message: String) {
        val time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss a"))
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
        storageManager.log += "${LogLevel.Debug.symbol} | $date $time : $message\n"
    }

}