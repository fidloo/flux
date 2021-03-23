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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class FetchHourlyWeather @Inject constructor(
    private val repository: WeatherRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : FlowUseCase<FetchHourlyWeather.Parameters, HourlyWeather>(dispatcher) {

    override fun execute(parameters: Parameters): Flow<Result<HourlyWeather>> {
        return flow {
            emit(Result.Loading)
            val weatherPerHour = repository.fetchHourlyWeather()
            val hourlyWeatherCurvePoints = computeHourlyWeatherCurvePoints(
                hourlyWeather = weatherPerHour,
                parameters.type,
            )

            val newPoints = hourlyWeatherCurvePoints.points
            val oldPoints = if (parameters.oldPoints.isNotEmpty()) {
                parameters.oldPoints
            } else {
                newPoints.map { it.copy(y = 0f) }
            }

            val size = oldPoints.size
            var maxDiffY = 0f
            for (i in 0 until size) {
                val abs = abs(oldPoints[i].y - newPoints[i].y)
                if (abs > maxDiffY) maxDiffY = abs
            }

            val loopCount = maxDiffY / 16
            val tempPointsForAnimation = mutableListOf<MutableList<Point>>()

            for (i in 0 until size) {
                val old = oldPoints[i]
                val new = newPoints[i]

                val plusOrMinusAmount = abs(new.y - old.y) / maxDiffY * 16

                var tempY = old.y
                val tempList = mutableListOf<Point>()

                for (j in 0..loopCount.toInt()) {
                    if (tempY == new.y) {
                        tempList.add(Point(new.x, new.y))
                    } else {
                        if (new.y > old.y) {
                            tempY += plusOrMinusAmount
                            tempY = min(tempY, new.y)
                            tempList.add(Point(new.x, tempY))
                        } else {
                            tempY -= plusOrMinusAmount
                            tempY = max(tempY, new.y)
                            tempList.add(Point(new.x, tempY))
                        }
                    }
                }
                tempPointsForAnimation.add(tempList)
            }

            val first = tempPointsForAnimation[0]
            val length = first.size

            for (i in 0 until length) {
                emit(
                    Result.Success(
                        HourlyWeather(
                            weatherPerHour = weatherPerHour,
                            hourlyWeatherCurvePoints = computeConnectionPoints(
                                tempPointsForAnimation.map { it[i] }.toMutableList()
                            )
                        )
                    )
                )
                delay(16)
            }
        }
    }

    // Compute bezier curve points in IO thread
    private fun computeHourlyWeatherCurvePoints(
        hourlyWeather: List<HourWeather>,
        type: HourlyWeatherType,
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

        return computeConnectionPoints(points)
    }

    private fun computeConnectionPoints(points: MutableList<Point>): HourlyWeatherCurvePoints {
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

    private fun getValueForType(weather: HourWeather, type: HourlyWeatherType): Float {
        return when (type) {
            HourlyWeatherType.Temperature -> weather.facts.temperature
            HourlyWeatherType.Wind -> weather.facts.windSpeed
            HourlyWeatherType.CloudCover -> weather.facts.cloudCover * 100
        }
    }

    data class Parameters(
        val type: HourlyWeatherType,
        val oldPoints: List<Point>
    )
}

object HourlyWeatherCurveParameters {
    const val cellSize = 72
    const val offsetTop = 56
    const val offsetBottom = 32
    const val heightInterval = 320
}
