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
package com.fidloo.flux.domain.model

data class WeatherFacts(
    val temperature: Int,
    val apparentTemperature: Int,
    val precipitation: Float,
    val humidity: Float,
    val windSpeed: Float,
    val cloudCover: Float,
    val pressure: Float,
    val visibility: Float,
    val uvIndex: Int,
    val dewPoint: Int,
    val state: WeatherState,
) {
    companion object {
        val Default = WeatherFacts(
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
    }
}
