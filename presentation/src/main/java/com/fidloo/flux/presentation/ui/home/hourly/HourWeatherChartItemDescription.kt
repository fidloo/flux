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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.model.WeatherFacts
import com.fidloo.flux.presentation.ui.utils.getDescriptionRes
import com.fidloo.flux.presentation.ui.utils.getIconRes
import java.util.Calendar
import java.util.Date

@Composable
fun HourWeatherChartItemDescription(
    item: HourWeather,
    selectedTime: Date,
    modifier: Modifier = Modifier,
    onWeatherTimeSelected: (Date) -> Unit
) {

    val calendar = Calendar.getInstance()
    calendar.time = selectedTime
    val selectedHour = calendar[Calendar.HOUR_OF_DAY]
    calendar.time = item.time
    val itemHour = calendar[Calendar.HOUR_OF_DAY]
    val isSelected = selectedHour == itemHour

    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.2f) else Color.Transparent,
        elevation = 0.dp,
        border = if (selectedHour == itemHour) {
            BorderStroke(2.dp, MaterialTheme.colors.primary)
        } else {
            null
        }
    ) {
        Column(
            modifier = modifier
                .clickable { onWeatherTimeSelected(item.time) }
                .padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(item.facts.state.getIconRes(item.night)),
                contentDescription = stringResource(id = item.facts.state.getDescriptionRes()),
                modifier = Modifier.size(36.dp),
            )

            calendar.time = item.time
            val currentHour = calendar[Calendar.HOUR_OF_DAY]
            Text(
                text = "%02d".format(currentHour),
                style = MaterialTheme.typography.h2
            )
        }
    }
}

@Preview
@Composable
fun HourWeatherChartItemDescriptionPreview() {
    val item = HourWeather(
        time = Date(),
        facts = WeatherFacts.Default,
        night = false
    )
    HourWeatherChartItemDescription(item, Date(), onWeatherTimeSelected = {})
}
