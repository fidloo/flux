package com.fidloo.flux.presentation.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableSectionHeader(
    title: String,
    subtitle: String,
    expanded: Boolean,
    onToggleState: () -> Unit
) {
    val rotation: Float by animateFloatAsState(if (expanded) 180f else 0f)

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleState() }
            .padding(horizontal = 24.dp, vertical = 16.dp)) {
        Column {
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
        Icon(
            imageVector = Icons.Rounded.ExpandMore,
            contentDescription = "Toggle expanded state",
            modifier = Modifier.size(28.dp)
                .rotate(rotation)
        )
    }

}

@Preview
@Composable
fun ExpandableSectionHeaderPreview() {
    ExpandableSectionHeader(
        title = "Hourly weather",
        subtitle = "24-hour forecast",
        expanded = false,
        onToggleState = {}
    )
}