package com.fidloo.flux.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SectionHeader(title: String, subtitle: String) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h1,
        )
        Spacer(modifier = Modifier.height(2.dp))
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.subtitle1,
            )
        }
    }
}

@Preview
@Composable
fun SectionHeaderPreview() {
    SectionHeader(title = "Hourly weather", subtitle = "24-hour forecast")
}