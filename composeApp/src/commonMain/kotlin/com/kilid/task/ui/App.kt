package com.kilid.task.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.kilid.task.ui.navigation.ApplicationNavHost
import com.kilid.task.ui.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    KoinContext {
        Theme {
            val navController = rememberNavController()

            ApplicationNavHost(navController)
        }
    }
}