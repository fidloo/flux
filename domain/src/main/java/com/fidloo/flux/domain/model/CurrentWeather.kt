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

import java.util.Calendar
import java.util.Date

data class CurrentWeather(
    val time: Date,
    val hourWeather: WeatherFacts,
    val sunrise: String,
    val sunset: String,
    val minTemperature: Int,
    val maxTemperature: Int,
) {
    companion object {

        fun getDefault(): CurrentWeather {
            val calendar = Calendar.getInstance()
            calendar[Calendar.MINUTE] = 0
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0

            return CurrentWeather(
                time = calendar.time,
                hourWeather = WeatherFacts.Default,
                sunrise = "06:46",
                sunset = "18:53",
                minTemperature = -1,
                maxTemperature = 9
            )
        }
    }
}
