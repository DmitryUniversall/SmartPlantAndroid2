package com.smartplant.core_android.data

import android.content.SharedPreferences
import com.smartplant.core_android.utils.logs.AppLogger
import kotlinx.serialization.json.Json

abstract class BasePrefsDataSource(
    protected val prefs: SharedPreferences,
) {
    protected val cache = mutableMapOf<String, Any?>()

    fun clearCache() {
        cache.clear()
    }

    fun clear() {
        prefs.edit().clear().apply()
        cache.clear()
    }

    fun clearKey(key: String) {
        prefs.edit().remove(key).apply()
        cache.remove(key)
    }

    protected fun getString(key: String): String? {
        return (cache[key] as? String)
            ?: prefs.getString(key, null)
                ?.also { cache[key] = it }
    }

    protected fun getBoolean(key: String, default: Boolean = false): Boolean {
        return prefs.getBoolean(key, default)
    }

    protected fun getInt(key: String, default: Int = 0): Int {
        return prefs.getInt(key, default)
    }

    protected fun saveInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    protected fun saveBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    protected fun saveString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
        cache[key] = value
    }

    protected inline fun <reified T> getObject(key: String): T? {
        @Suppress("UNCHECKED_CAST")
        return (cache[key] as? T)
            ?: prefs.getString(key, null)
                ?.let { Json.decodeFromString<T>(it) }
                ?.also { cache[key] = it }
    }

    protected inline fun <reified T> saveObject(key: String, value: T) {
        val json: String = Json.encodeToString(value)
        prefs.edit().putString(key, json).apply()
        cache[key] = value
    }
}
