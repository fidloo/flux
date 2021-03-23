/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fidloo.flux.presentation.ui.home.hourly

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fidloo.flux.presentation.R

@Composable
fun HourlyWeatherFilter(
    item: HourlyWeatherType,
    selected: Boolean,
    onFilterSelected: (HourlyWeatherType) -> Unit
) {
    Box(modifier = Modifier.padding(8.dp)) {
        Card(
            shape = MaterialTheme.shapes.small,
            backgroundColor = if (selected) {
                MaterialTheme.colors.primary.copy(alpha = 0.2f)
            } else {
                Color.Transparent
            },
            elevation = 0.dp,
            border = if (selected) {
                BorderStroke(2.dp, MaterialTheme.colors.primary)
            } else {
                null
            },
        ) {
            Text(
                modifier = Modifier
                    .clickable { onFilterSelected(item) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = when (item) {
                    HourlyWeatherType.Temperature -> stringResource(id = R.string.temperature)
                    HourlyWeatherType.Wind -> stringResource(id = R.string.wind)
                    HourlyWeatherType.Rain -> stringResource(id = R.string.rain)
                },
                style = MaterialTheme.typography.h3.copy(
                    fontWeight = if (selected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }
                ),
            )
        }
    }
}

@Preview
@Composable
fun HourlyWeatherFilterPreview() {
    HourlyWeatherFilter(
        item = HourlyWeatherType.Temperature,
        selected = true,
        onFilterSelected = {}
    )
}
