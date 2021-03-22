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
package com.fidloo.flux.data.datasource

import com.fidloo.flux.domain.model.CurrentWeather
import com.fidloo.flux.domain.model.DayWeather
import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.model.WeatherFacts
import com.fidloo.flux.domain.model.WeatherState
import java.util.Calendar
import java.util.Date

object FakeWeatherDataSource {

    fun getCurrentWeather(): CurrentWeather {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val hourFacts = getNext24HoursWeather().first { it.hour == hour }.facts
        return CurrentWeather(
            time = Date(),
            hourWeather = hourFacts,
            sunrise = "06:46",
            sunset = "18:53",
            minTemperature = -1,
            maxTemperature = 9
        )
    }

    fun getNext24HoursWeather(): List<HourWeather> {
        val currentHour = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
        val next24HourWeather = listOf(
            HourWeather(
                0,
                WeatherFacts(
                    temperature = 2,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                1,
                WeatherFacts(
                    temperature = 1,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.SCATTERED_CLOUDS
                )
            ),
            HourWeather(
                2,
                WeatherFacts(
                    temperature = 0,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.FEW_CLOUDS
                )
            ),
            HourWeather(
                3,
                WeatherFacts(
                    temperature = 0,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.FOG
                )
            ),
            HourWeather(
                4,
                WeatherFacts(
                    temperature = -1,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.HEAVY_RAIN
                )
            ),
            HourWeather(
                5,
                WeatherFacts(
                    temperature = -1,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.RAIN
                )
            ),
            HourWeather(
                6,
                WeatherFacts(
                    temperature = 0,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.MOSTLY_CLOUDY
                )
            ),
            HourWeather(
                7,
                WeatherFacts(
                    temperature = 1,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.SNOW
                )
            ),
            HourWeather(
                8,
                WeatherFacts(
                    temperature = 2,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.THUNDERSTORM
                )
            ),
            HourWeather(
                9,
                WeatherFacts(
                    temperature = 3,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                10,
                WeatherFacts(
                    temperature = 5,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                11,
                WeatherFacts(
                    temperature = 6,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                12,
                WeatherFacts(
                    temperature = 7,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                13,
                WeatherFacts(
                    temperature = 8,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                14,
                WeatherFacts(
                    temperature = 8,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                15,
                WeatherFacts(
                    temperature = 8,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                16,
                WeatherFacts(
                    temperature = 8,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                17,
                WeatherFacts(
                    temperature = 9,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                18,
                WeatherFacts(
                    temperature = 8,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                19,
                WeatherFacts(
                    temperature = 6,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                20,
                WeatherFacts(
                    temperature = 5,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                21,
                WeatherFacts(
                    temperature = 4,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                22,
                WeatherFacts(
                    temperature = 3,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
            HourWeather(
                23,
                WeatherFacts(
                    temperature = 2,
                    apparentTemperature = 2,
                    precipitation = 0.2f,
                    humidity = 0.49f,
                    windSpeed = 17f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                )
            ),
        )
        val (part1, part2) = next24HourWeather.partition { it.hour >= currentHour }
        return part1 + part2
    }

    fun Calendar.nextDay(): Date {
        add(Calendar.DAY_OF_YEAR, 1)
        return time
    }

    fun getNext7dayWeather(): List<DayWeather> {
        val now = Calendar.getInstance()

        return listOf(
            DayWeather(
                date = now.nextDay(),
                facts = WeatherFacts(
                    temperature = 4,
                    apparentTemperature = -1,
                    precipitation = 0.0f,
                    humidity = 0.49f,
                    windSpeed = 48f,
                    cloudCover = 0.13f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 3,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                ),
                sunrise = "06:44",
                sunset = "18:54",
                minTemperature = -1,
                maxTemperature = 10
            ),
            DayWeather(
                date = now.nextDay(),
                facts = WeatherFacts(
                    temperature = 4,
                    apparentTemperature = -1,
                    precipitation = 0.0f,
                    humidity = 0.49f,
                    windSpeed = 20f,
                    cloudCover = 0.13f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 4,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                ),
                sunrise = "06:42",
                sunset = "18:55",
                minTemperature = 4,
                maxTemperature = 12
            ),
            DayWeather(
                date = now.nextDay(),
                facts = WeatherFacts(
                    temperature = 4,
                    apparentTemperature = -1,
                    precipitation = 0.01f,
                    humidity = 0.49f,
                    windSpeed = 19f,
                    cloudCover = 0.57f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 1,
                    dewPoint = -4,
                    state = WeatherState.MOSTLY_CLOUDY
                ),
                sunrise = "06:41",
                sunset = "18:57",
                minTemperature = 0,
                maxTemperature = 12
            ),
            DayWeather(
                date = now.nextDay(),
                facts = WeatherFacts(
                    temperature = 4,
                    apparentTemperature = -1,
                    precipitation = 0.01f,
                    humidity = 0.49f,
                    windSpeed = 11f,
                    cloudCover = 0.07f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 4,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                ),
                sunrise = "06:39",
                sunset = "18:58",
                minTemperature = -1,
                maxTemperature = 16
            ),
            DayWeather(
                date = now.nextDay(),
                facts = WeatherFacts(
                    temperature = 4,
                    apparentTemperature = -1,
                    precipitation = 0.0f,
                    humidity = 0.49f,
                    windSpeed = 5f,
                    cloudCover = 0.0f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 5,
                    dewPoint = -4,
                    state = WeatherState.CLEAR_SKY
                ),
                sunrise = "06:37",
                sunset = "18:59",
                minTemperature = 0,
                maxTemperature = 19
            ),
            DayWeather(
                date = now.nextDay(),
                facts = WeatherFacts(
                    temperature = 4,
                    apparentTemperature = -1,
                    precipitation = 0.01f,
                    humidity = 0.49f,
                    windSpeed = 8f,
                    cloudCover = 0.72f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 4,
                    dewPoint = -4,
                    state = WeatherState.SCATTERED_CLOUDS
                ),
                sunrise = "06:35",
                sunset = "19h01",
                minTemperature = 9,
                maxTemperature = 21
            ),
            DayWeather(
                date = now.nextDay(),
                facts = WeatherFacts(
                    temperature = 4,
                    apparentTemperature = -1,
                    precipitation = 0.11f,
                    humidity = 0.49f,
                    windSpeed = 18f,
                    cloudCover = 0.88f,
                    pressure = 1.0f,
                    visibility = 5f,
                    uvIndex = 3,
                    dewPoint = -4,
                    state = WeatherState.MOSTLY_CLOUDY
                ),
                sunrise = "06:33",
                sunset = "19h02",
                minTemperature = 12,
                maxTemperature = 22
            ),
        )
    }
}
