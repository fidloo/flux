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

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.model.HourlyWeather
import com.fidloo.flux.domain.model.HourlyWeatherCurvePoints
import com.fidloo.flux.domain.model.WeatherFacts
import java.util.Calendar
import java.util.Date

@Composable
fun HourlyWeatherChart(
    hourlyWeather: HourlyWeather,
    selectedTime: Date,
    onWeatherTimeSelected: (Date) -> Unit,
    expanded: Boolean
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        Column(modifier = Modifier.animateContentSize()) {
            if (expanded) {
                HourlyWeatherCurve(hourlyWeather)
            }
            HourlyWeatherChartHorizontalAxisDescription(
                hourlyWeather,
                selectedTime,
                onWeatherTimeSelected
            )
        }
    }
}

@Preview
@Composable
fun HourlyWeatherChartPreview() {
    val calendar = Calendar.getInstance()
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0

    val weatherPerHour = (1..10).map { index ->
        if (index != 0) {
            calendar.add(Calendar.HOUR, 1)
        }

        HourWeather(
            time = calendar.time,
            facts = WeatherFacts.Default.copy(temperature = index),
            night = false
        )
    }
    val item = HourlyWeather(
        weatherPerHour = weatherPerHour,
        hourlyWeatherCurvePoints = HourlyWeatherCurvePoints()
    )
    HourlyWeatherChart(item, Date(), {}, true)
}
