package com.hackerwall.service

import android.util.Log
import com.hackerwall.base.parseJson
import com.hackerwall.base.request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class EsbCalService {
    //https://android--code.blogspot.com/2020/07/android-kotlin-coroutines-json-from-url.html
    suspend fun getCal(): List<Map<String, String>> {
        val url = URL("https://home-remote-api.herokuapp.com/esb/cal")
        // io dispatcher for networking operation
        url.request().apply {
            val result = this
            // default dispatcher for json parsing, cpu intensive work
            return withContext(Dispatchers.Default) {
                return@withContext result?.parseJson()!!
            }
        }

    }

    suspend fun getToday(): Map<String, String>? {
        val now = LocalDate.now()
        val formattedDate: String = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        return getCal().find { return@find it["date"] == formattedDate }
    }
}