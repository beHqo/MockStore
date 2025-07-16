package com.kilid.task.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.kilid.task.presentation.navigation.ApplicationNavHost
import com.kilid.task.presentation.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    Theme {
        val navController = rememberNavController()

        ApplicationNavHost(navController)
    }
}