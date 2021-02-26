package com.hackerwall.storage

import android.content.Context
import android.content.SharedPreferences
import kotlin.reflect.KProperty

class DelegatedSharedPreference<T>(
    private val context: Context,
    private val name: String,
    private val default: T
) {
    val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = findPreferences(name, default)
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = savePreference(name, value)

    @Suppress("UNCHECKED_CAST")
    fun <T> findPreferences(key: String, defaultValue: T): T {
        with(prefs) {
            val result: Any = when (defaultValue) {
                is Boolean -> getBoolean(key, defaultValue)
                is Int -> getInt(key, defaultValue)
                is Long -> getLong(key, defaultValue)
                is Float -> getFloat(key, defaultValue)
                is String -> getString(key, defaultValue) ?: ""
                else -> throw IllegalArgumentException()
            }
            return result as T
        }
    }

    fun <T> savePreference(key: String, value: T?): Boolean {
        return with(prefs.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException()
            }.commit()
        }
    }

    fun deletePreference(key: String) = prefs.edit().remove(key).apply()
}