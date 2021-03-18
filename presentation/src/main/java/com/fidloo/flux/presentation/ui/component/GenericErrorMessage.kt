package com.fidloo.flux.presentation.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenericErrorMessage() {
    Text(
        text = "An error occurred.",
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)
    )
}