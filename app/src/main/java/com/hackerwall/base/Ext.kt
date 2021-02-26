package com.hackerwall.base

import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL


// extension function to get string data from url
fun URL.request(): String? {
    val stream = openStream()
    return try {
        val r = BufferedReader(InputStreamReader(stream))
        val result = StringBuilder()
        var line: String?
        while (r.readLine().also { line = it } != null) {
            result.append(line).appendLine()
        }
        result.toString()
    } catch (e: IOException) {
        e.toString()
    }
}

// parse json data
fun String.parseJson(): List<Map<String, String>> {
    try {
        val result = mutableListOf<Map<String, String>>()
        val jArr = JSONArray(this)

        for (i in 0 until jArr.length()) {
            val item = jArr.getJSONObject(i)
            val keys = item.keys()
            val map = HashMap<String, String>()
            while (keys.hasNext()) {
                val key = keys.next() as String
                val value: String = item.getString(key)
                map[key] = value
            }
            result.add(map)
        }

        return result
    } catch (e: JSONException) {
        Log.d("Exception", e.toString())
        return listOf()
    }
}


inline fun View.fadeIn(durationMillis: Long = 500) {
    this.visibility = View.VISIBLE
    this.startAnimation(AlphaAnimation(0F, 1F).apply {
        duration = durationMillis
        fillAfter = true
    })
}

inline fun View.fadeOut(durationMillis: Long = 250) {
    this.startAnimation(AlphaAnimation(1F, 0F).apply {
        duration = durationMillis
        fillAfter = true
    })
}