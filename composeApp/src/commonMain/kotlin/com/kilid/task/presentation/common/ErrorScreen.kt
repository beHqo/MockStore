package com.kilid.task.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * A generic error screen that displays a centered error message and a refresh button.
 *
 * @param message The error message to show.
 * @param onRefresh Callback invoked when the user taps the retry button.
 * @param modifier Optional [Modifier] for the root container.
 * @param refreshButtonText Text to show on the refresh button.
 */
@Composable
fun ErrorScreen(
    message: String, onRefresh: () -> Unit, modifier: Modifier = Modifier, refreshButtonText: String
) = Box(
    modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.fillMaxWidth().padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Button(onClick = onRefresh) {
                Text(text = refreshButtonText, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}