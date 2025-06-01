package com.kilid.task.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.kilid.task.ui.navigation.ApplicationNavHost
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext

@Composable
@Preview
fun App() {
    KoinContext {
        MaterialTheme {
            val navController = rememberNavController()

            ApplicationNavHost(navController)
        }
    }
}