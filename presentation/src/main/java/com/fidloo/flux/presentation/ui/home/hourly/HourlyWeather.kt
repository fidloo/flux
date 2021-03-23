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
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import com.fidloo.flux.domain.base.Result
import com.fidloo.flux.domain.model.HourlyWeather
import com.fidloo.flux.domain.model.HourlyWeatherType
import com.fidloo.flux.presentation.R
import com.fidloo.flux.presentation.ui.component.ErrorMessage
import com.fidloo.flux.presentation.ui.component.ExpandableSectionHeader
import com.fidloo.flux.presentation.ui.component.SectionProgressBar
import java.util.Date

@Composable
fun HourlyWeather(
    hourlyWeatherResult: Result<HourlyWeather>,
    selectedTime: Date,
    selectedFilter: HourlyWeatherType,
    onWeatherTimeSelected: (Date) -> Unit,
    onFilterSelected: (HourlyWeatherType) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        ExpandableSectionHeader(
            title = stringResource(R.string.hourly_weather),
            subtitle = stringResource(R.string.forecast_24h),
            expanded = expanded,
            onToggleState = { expanded = !expanded }
        )
        Spacer(Modifier.height(8.dp))

        when (hourlyWeatherResult) {
            is Result.Error -> ErrorMessage()
            Result.Loading -> SectionProgressBar()
            is Result.Success -> {
                HourlyWeatherFilters(selectedFilter, onFilterSelected)
                HourlyWeatherChart(
                    hourlyWeatherResult.data,
                    selectedTime,
                    onWeatherTimeSelected,
                    expanded,
                    selectedFilter
                )
            }
        }
    }
}
