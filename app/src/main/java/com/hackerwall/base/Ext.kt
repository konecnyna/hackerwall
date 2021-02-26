package com.hackerwall.base

import android.util.Log
import kotlinx.coroutines.Dispatchers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
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
        Log.d("test1234", result.toString())
        result.toString()
    } catch (e: IOException) {
        e.toString()
    }
}

// parse json data
fun String.parseJson(): List<Map<String, String>> {
    try {
        val result = mutableListOf<Map<String,String>>()
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

