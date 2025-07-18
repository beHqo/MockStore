package com.kilid.task.presentation

import androidx.compose.ui.window.ComposeUIViewController
import com.kilid.task.di.initKoin

fun MainViewController() = ComposeUIViewController(configure = { initKoin() }) { App() }