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
package com.fidloo.flux.presentation.ui.home.current

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fidloo.flux.domain.model.CurrentWeather
import com.fidloo.flux.presentation.R
import com.fidloo.flux.presentation.ui.component.ExpandableSectionHeader

@Composable
fun CurrentWeatherSection(currentWeather: CurrentWeather) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        ExpandableSectionHeader(
            title = stringResource(R.string.details),
            subtitle = stringResource(R.string.weather_now),
            expanded = expanded,
            onToggleState = { expanded = !expanded }
        )
        Spacer(Modifier.height(8.dp))

        val weatherFacts = currentWeather.extractFacts()

        val itemsPerRow = 2
        weatherFacts.chunked(itemsPerRow)
            .run { if (!expanded) take(2) else this }
            .forEach { factsPerRow ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    factsPerRow.forEachIndexed { index, fact ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(1f / (itemsPerRow - index))
                        ) {
                            WeatherFact(fact)
                        }
                    }
                }
            }
    }
}

@Preview
@Composable
fun CurrentWeatherPreview() {
    CurrentWeatherSection(CurrentWeather.getDefault())
}
