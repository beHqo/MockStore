package com.kilid.task

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform