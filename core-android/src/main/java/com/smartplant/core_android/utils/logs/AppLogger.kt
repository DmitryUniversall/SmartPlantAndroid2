package com.smartplant.core_android.utils.logs

import android.util.Log

object AppLogger {
    private val currentStackTraceElement: StackTraceElement
        get() = Thread.currentThread().stackTrace[6]  // TODO: auto stack index

    private val StackTraceElement.tag: String
        get() = "${fileName}:${lineNumber}"

    private fun log(priority: Int, message: String, throwable: Throwable? = null) {
        val element = currentStackTraceElement

        if (throwable != null) {
            Log.println(priority, element.tag, "$message\n${Log.getStackTraceString(throwable)}")
        } else {
            Log.println(priority, element.tag, message)
        }
    }

    operator fun invoke(message: String) = debug(message)

    infix fun verbose(msg: String) = log(Log.VERBOSE, msg)
    infix fun debug(msg: String) = log(Log.DEBUG, msg)
    infix fun info(msg: String) = log(Log.INFO, msg)
    infix fun warning(msg: String) = log(Log.WARN, msg)
    infix fun error(msg: String) = log(Log.ERROR, msg)

    fun Any.logAsVerbose() = log(Log.VERBOSE, toString())
    fun Any.logAsDebug() = log(Log.DEBUG, toString())
    fun Any.logAsInfo() = log(Log.INFO, toString())
    fun Any.logAsWarning() = log(Log.WARN, toString())
    fun Any.logAsError() = log(Log.ERROR, toString())

    fun verbose(message: String, throwable: Throwable? = null) = log(Log.VERBOSE, message, throwable)
    fun debug(message: String, throwable: Throwable? = null) = log(Log.DEBUG, message, throwable)
    fun info(message: String, throwable: Throwable? = null) = log(Log.INFO, message, throwable)
    fun warning(message: String, throwable: Throwable? = null) = log(Log.WARN, message, throwable)
    fun error(message: String, throwable: Throwable? = null) = log(Log.ERROR, message, throwable)

    fun verbose(format: String, vararg args: Any) = verbose(format.format(*args))
    fun debug(format: String, vararg args: Any) = debug(format.format(*args))
    fun info(format: String, vararg args: Any) = info(format.format(*args))
    fun warning(format: String, vararg args: Any) = warning(format.format(*args))
    fun error(format: String, vararg args: Any) = error(format.format(*args))
}
