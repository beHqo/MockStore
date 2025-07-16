package com.kilid.task.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kilidtask.composeapp.generated.resources.Res
import kilidtask.composeapp.generated.resources.round_star_half_24
import kilidtask.composeapp.generated.resources.round_star_outline_24
import kilidtask.composeapp.generated.resources.round_star_rate_24
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RatingBar(
    rating: Double, count: Int, modifier: Modifier = Modifier
) = Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
    Rating(rating = rating)

    Spacer(modifier = Modifier.width(4.dp))

    Text(
        text = "($count)",
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun Rating(rating: Double, modifier: Modifier = Modifier) = repeat(5) { i ->
    val starIcon = vectorResource(
        when {
            i < rating.toInt() -> Res.drawable.round_star_rate_24
            i < rating -> Res.drawable.round_star_half_24
            else -> Res.drawable.round_star_outline_24
        }
    )

    Icon(
        imageVector = starIcon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = modifier.size(16.dp)
    )
}

@Preview
@Composable
private fun RatingBarPreview() {
    MaterialTheme {
        Surface {
            Box(Modifier.fillMaxSize()) {
                RatingBar(
                    rating = 3.1,
                    count = 1289,
                    modifier = Modifier.padding(top = 64.dp).align(Alignment.Center)
                )
            }
        }
    }
}