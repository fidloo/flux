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
package com.fidloo.flux.data.repository

import com.fidloo.flux.data.datasource.FakeWeatherDataSource
import com.fidloo.flux.domain.model.CurrentWeather
import com.fidloo.flux.domain.model.DayWeather
import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.repository.WeatherRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor() : WeatherRepository {
    override suspend fun fetchCurrentWeather(): CurrentWeather {
        // Simulate network delay
        delay(400L)
        return FakeWeatherDataSource.getCurrentWeather()
    }

    override suspend fun fetchHourlyWeather(): List<HourWeather> {
        // Simulate network delay
        delay(200L)
        return FakeWeatherDataSource.next24HourWeather
    }

    override suspend fun fetchWeekWeather(): List<DayWeather> {
        // Simulate network delay
        delay(600L)
        return FakeWeatherDataSource.getNext7dayWeather()
    }
}
