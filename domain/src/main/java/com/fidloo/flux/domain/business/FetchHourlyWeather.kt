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
package com.fidloo.flux.domain.business

import com.fidloo.flux.domain.base.FlowUseCase
import com.fidloo.flux.domain.base.Result
import com.fidloo.flux.domain.di.IoDispatcher
import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.model.HourlyWeather
import com.fidloo.flux.domain.model.HourlyWeatherCurvePoints
import com.fidloo.flux.domain.model.HourlyWeatherType
import com.fidloo.flux.domain.model.Point
import com.fidloo.flux.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchHourlyWeather @Inject constructor(
    private val repository: WeatherRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<HourlyWeatherType, HourlyWeather>(dispatcher) {

    override fun execute(parameters: HourlyWeatherType): Flow<Result<HourlyWeather>> {
        return flow {
            emit(Result.Loading)
            val weatherPerHour = repository.fetchHourlyWeather()
            val hourlyWeatherCurvePoints = computeHourlyWeatherCurvePoints(
                hourlyWeather = weatherPerHour,
                parameters
            )

            emit(
                Result.Success(
                    HourlyWeather(
                        weatherPerHour = weatherPerHour,
                        hourlyWeatherCurvePoints = hourlyWeatherCurvePoints
                    )
                )
            )
        }
    }

    private fun getValueForType(weather: HourWeather, type: HourlyWeatherType): Float {
        return when (type) {
            HourlyWeatherType.Temperature -> weather.facts.temperature
            HourlyWeatherType.Wind -> weather.facts.windSpeed
            HourlyWeatherType.CloudCover -> weather.facts.cloudCover * 100
        }
    }

    // Compute bezier curve points in IO thread
    private fun computeHourlyWeatherCurvePoints(
        hourlyWeather: List<HourWeather>,
        type: HourlyWeatherType
    ): HourlyWeatherCurvePoints {
        val cellSize = HourlyWeatherCurveParameters.cellSize
        val offsetY = HourlyWeatherCurveParameters.offsetTop
        val chartHeight = HourlyWeatherCurveParameters.heightInterval
        val chartTopPadding = HourlyWeatherCurveParameters.offsetTop
        val curveBottomOffset = HourlyWeatherCurveParameters.offsetBottom
        val minY = hourlyWeather.minOf { getValueForType(it, type) }
        val maxY = hourlyWeather.maxOf { getValueForType(it, type) }
        val heightStep =
            (chartHeight - (chartTopPadding + curveBottomOffset)) / (maxY - minY)

        val points = hourlyWeather.mapIndexed { index, item ->
            Point(
                (index.toFloat() + 1) * cellSize,
                (maxY - getValueForType(item, type)) * heightStep + offsetY
            )
        }.toMutableList()
        points.add(0, points.first().copy(x = 0f))
        val lastPoint = points.last()
        points.add(lastPoint.copy(x = lastPoint.x + cellSize))

        val connectionPoints1 = mutableListOf<Point>()
        val connectionPoints2 = mutableListOf<Point>()

        try {
            for (i in 1 until points.size) {
                connectionPoints1.add(
                    Point(
                        (points[i].x + points[i - 1].x) / 2,
                        points[i - 1].y
                    )
                )
                connectionPoints2.add(
                    Point(
                        (points[i].x + points[i - 1].x) / 2,
                        points[i].y
                    )
                )
            }
        } catch (e: Exception) {
        }

        return HourlyWeatherCurvePoints(
            points,
            connectionPoints1,
            connectionPoints2
        )
    }
}

object HourlyWeatherCurveParameters {
    const val cellSize = 72
    const val offsetTop = 56
    const val offsetBottom = 32
    const val heightInterval = 320
}
