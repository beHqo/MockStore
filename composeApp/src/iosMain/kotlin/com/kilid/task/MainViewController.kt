package com.kilid.task

import androidx.compose.ui.window.ComposeUIViewController
import com.kilid.task.di.initKoin
import com.kilid.task.ui.App

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }