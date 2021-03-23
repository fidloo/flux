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
package com.fidloo.flux.presentation.ui.utils

import com.fidloo.flux.domain.model.WeatherState
import com.fidloo.flux.presentation.R

fun WeatherState.getDescriptionRes(): Int {
    return when (this) {
        WeatherState.CLEAR_SKY -> R.string.clear_sky
        WeatherState.FEW_CLOUDS -> R.string.few_clouds
        WeatherState.SCATTERED_CLOUDS -> R.string.scattered_clouds
        WeatherState.MOSTLY_CLOUDY -> R.string.mostly_cloudy
        WeatherState.RAIN -> R.string.rainy
        WeatherState.HEAVY_RAIN -> R.string.heavy_rain
        WeatherState.THUNDERSTORM -> R.string.thunderstorm
        WeatherState.SNOW -> R.string.snowy
        WeatherState.FOG -> R.string.foggy
    }
}

fun WeatherState.getIconRes(nightMode: Boolean = false): Int {
    return when (this) {
        WeatherState.CLEAR_SKY -> if (nightMode) R.drawable.ic_night_clear else R.drawable.ic_sunny
        WeatherState.FEW_CLOUDS -> if (nightMode) R.drawable.ic_night_alt_partly_cloudy else R.drawable.ic_day_sunny_overcast
        WeatherState.SCATTERED_CLOUDS -> if (nightMode) R.drawable.ic_night_alt_cloudy else R.drawable.ic_day_cloudy
        WeatherState.MOSTLY_CLOUDY -> R.drawable.ic_cloudy
        WeatherState.RAIN -> if (nightMode) R.drawable.ic_night_alt_showers else R.drawable.ic_day_showers
        WeatherState.HEAVY_RAIN -> if (nightMode) R.drawable.ic_night_alt_rain else R.drawable.ic_day_rain
        WeatherState.THUNDERSTORM -> if (nightMode) R.drawable.ic_night_alt_thunderstorm else R.drawable.ic_day_thunderstorm
        WeatherState.SNOW -> if (nightMode) R.drawable.ic_night_alt_snow else R.drawable.ic_day_snow
        WeatherState.FOG -> if (nightMode) R.drawable.ic_night_fog else R.drawable.ic_day_fog
    }
}
