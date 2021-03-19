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
package com.fidloo.flux.presentation.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.FilterDrama
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Opacity
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.Umbrella
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.Water
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WbTwilight
import androidx.compose.material.icons.rounded.Air
import androidx.compose.ui.graphics.vector.ImageVector
import com.fidloo.flux.domain.model.CurrentWeather
import com.fidloo.flux.domain.model.DayWeather

data class WeatherFact(
    val label: String,
    val value: String,
    val icon: ImageVector,
)

fun CurrentWeather.extractFacts() = listOf(
    WeatherFact(
        label = "Temperature",
        value = "$maxTemperature°  |  $minTemperature°",
        icon = Icons.Outlined.Thermostat
    ),
    WeatherFact(
        label = "Feels like",
        value = "${hourWeather.apparentTemperature}°",
        icon = Icons.Outlined.Face
    ),
    WeatherFact(
        label = "Wind speed",
        value = "${hourWeather.windSpeed} km/h",
        icon = Icons.Rounded.Air
    ),
    WeatherFact(
        label = "Precipitation",
        value = "${hourWeather.precipitation * 100}%",
        icon = Icons.Outlined.Umbrella
    ),
    WeatherFact(
        label = "Cloud cover",
        value = "${hourWeather.cloudCover * 100}%",
        icon = Icons.Outlined.FilterDrama
    ),
    WeatherFact(
        label = "Humidity",
        value = "${hourWeather.humidity * 100}%",
        icon = Icons.Outlined.Opacity
    ),
    WeatherFact(
        label = "Pressure",
        value = "${hourWeather.pressure} bar",
        icon = Icons.Outlined.Speed
    ),
    WeatherFact(
        label = "UV Index",
        value = "${hourWeather.uvIndex}",
        icon = Icons.Outlined.WbSunny
    ),
    WeatherFact(
        label = "Visibility",
        value = "${hourWeather.visibility} km",
        icon = Icons.Outlined.Visibility
    ),
    WeatherFact(
        label = "Dew Point",
        value = "${hourWeather.dewPoint}°",
        icon = Icons.Outlined.Water
    ),
    WeatherFact(
        label = "Sunrise",
        value = sunrise,
        icon = Icons.Outlined.LightMode
    ),
    WeatherFact(
        label = "Sunset",
        value = sunset,
        icon = Icons.Outlined.WbTwilight
    ),
)

fun DayWeather.extractFacts() = listOf(
    WeatherFact(
        label = "Cloud cover",
        value = "${facts.cloudCover * 100}%",
        icon = Icons.Outlined.FilterDrama
    ),
    WeatherFact(
        label = "Wind speed",
        value = "${facts.windSpeed} km/h",
        icon = Icons.Rounded.Air
    ),
    WeatherFact(
        label = "Humidity",
        value = "${facts.humidity * 100}%",
        icon = Icons.Outlined.Opacity
    ),
    WeatherFact(
        label = "UV Index",
        value = "${facts.uvIndex}",
        icon = Icons.Outlined.WbSunny
    ),
    WeatherFact(
        label = "Sunrise",
        value = sunrise,
        icon = Icons.Outlined.LightMode
    ),
    WeatherFact(
        label = "Sunset",
        value = sunset,
        icon = Icons.Outlined.WbTwilight
    ),
)
