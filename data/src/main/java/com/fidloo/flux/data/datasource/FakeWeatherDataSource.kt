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

    fun fetchWeatherAtTime(time: Date): CurrentWeather {
        val calendar = Calendar.getInstance()
        calendar.time = time
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        val hourFacts = getNext24HoursWeather().first { it.time == calendar.time }.facts
        return CurrentWeather(
            time = time,
            hourWeather = hourFacts,
            sunrise = "06:46",
            sunset = "18:53",
            minTemperature = -1,
            maxTemperature = 9
        )
    }

    fun getNext24HoursWeather(): List<HourWeather> {
        val sunrise = "06:46"
        val sunset = "18:53"
        val (sunriseHour, sunriseMinute) = sunrise.split(":").map { it.toFloat() }
        val sunriseAt = sunriseHour + if (sunriseMinute > 30) 1 else 0
        val (sunsetHour, sunsetMinute) = sunset.split(":").map { it.toFloat() }
        val sunsetAt = sunsetHour + if (sunsetMinute > 30) 1 else 0

        val next24HourWeather = listOf(
            WeatherFacts(
                temperature = 2f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 6f,
                cloudCover = 0f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.CLEAR_SKY
            ),
            WeatherFacts(
                temperature = 1f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 5f,
                cloudCover = 0.13f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.FEW_CLOUDS
            ),
            WeatherFacts(
                temperature = 0f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 5f,
                cloudCover = 0.37f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.SCATTERED_CLOUDS
            ),
            WeatherFacts(
                temperature = 0f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 5f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.MOSTLY_CLOUDY
            ),
            WeatherFacts(
                temperature = -1f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 4f,
                cloudCover = 0.32f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.SNOW
            ),
            WeatherFacts(
                temperature = -1f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 4f,
                cloudCover = 0.30f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.SNOW
            ),
            WeatherFacts(
                temperature = 0f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 4f,
                cloudCover = 0.28f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.SNOW
            ),
            WeatherFacts(
                temperature = 1f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 4f,
                cloudCover = 0.26f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.SNOW
            ),
            WeatherFacts(
                temperature = 2f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 3f,
                cloudCover = 0.17f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.SNOW
            ),
            WeatherFacts(
                temperature = 3f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 3f,
                cloudCover = 0f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.CLEAR_SKY
            ),
            WeatherFacts(
                temperature = 5f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 3f,
                cloudCover = 0.14f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.RAIN
            ),
            WeatherFacts(
                temperature = 6f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 3f,
                cloudCover = 0.21f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.HEAVY_RAIN
            ),
            WeatherFacts(
                temperature = 7f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 4f,
                cloudCover = 0.45f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.THUNDERSTORM
            ),
            WeatherFacts(
                temperature = 8f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 4f,
                cloudCover = 0.32f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.RAIN
            ),
            WeatherFacts(
                temperature = 8f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 5f,
                cloudCover = 0.01f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.CLEAR_SKY
            ),
            WeatherFacts(
                temperature = 8f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 5f,
                cloudCover = 0f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.CLEAR_SKY
            ),
            WeatherFacts(
                temperature = 8f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 5f,
                cloudCover = 0f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.CLEAR_SKY
            ),
            WeatherFacts(
                temperature = 9f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 6f,
                cloudCover = 0f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.CLEAR_SKY
            ),
            WeatherFacts(
                temperature = 8f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 6f,
                cloudCover = 0f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.CLEAR_SKY
            ),
            WeatherFacts(
                temperature = 6f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 6f,
                cloudCover = 0f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.CLEAR_SKY
            ),
            WeatherFacts(
                temperature = 5f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 7f,
                cloudCover = 0.2f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.FOG
            ),
            WeatherFacts(
                temperature = 4f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 8f,
                cloudCover = 0.28f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.HEAVY_RAIN
            ),
            WeatherFacts(
                temperature = 3f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 11f,
                cloudCover = 0.48f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.THUNDERSTORM
            ),
            WeatherFacts(
                temperature = 2f,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 10f,
                cloudCover = 0.21f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
                state = WeatherState.RAIN
            ),
        )

        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        return next24HourWeather.mapIndexed { index, facts ->
            if (index != 0) {
                calendar.add(Calendar.HOUR, 1)
            }

            val hour = calendar[Calendar.HOUR_OF_DAY]
            HourWeather(
                time = calendar.time,
                facts = facts,
                night = hour < sunriseAt || hour > sunsetAt
            )
        }
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
                    temperature = 4f,
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
                    temperature = 4f,
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
                    temperature = 4f,
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
                    temperature = 4f,
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
                    temperature = 4f,
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
                    temperature = 4f,
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
                    temperature = 4f,
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
