package com.kilid.task.domain

import platform.Foundation.NSLog

actual object Log {
    actual fun i(tag: String, message: String, throwable: Throwable?) =
        NSLog(generateLog("INFO", tag, message, throwable))

    actual fun d(tag: String, message: String, throwable: Throwable?) =
        NSLog(generateLog("DEBUG", tag, message, throwable))

    actual fun w(tag: String, message: String, throwable: Throwable?) =
        NSLog(generateLog("WARNING", tag, message, throwable))

    actual fun e(tag: String, message: String, throwable: Throwable?) =
        NSLog(generateLog("ERROR", tag, message, throwable))
}

private fun generateLog(
    logLevel: String, tag: String, message: String, throwable: Throwable?
): String =
    "$logLevel: [$tag] $message".let { if (throwable != null) "$it\n${throwable.printStackTrace()}" else it }