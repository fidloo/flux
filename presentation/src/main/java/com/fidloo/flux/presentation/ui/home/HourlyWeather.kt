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

import android.graphics.Paint
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.fidloo.flux.domain.base.Result
import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.presentation.ui.component.GenericErrorMessage
import com.fidloo.flux.presentation.ui.component.SectionHeader
import com.fidloo.flux.presentation.ui.component.SectionProgressBar

@Composable
fun HourlyWeather(hourlyWeatherResult: Result<List<HourWeather>>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        SectionHeader(title = "Hourly weather", subtitle = "24-hour forecast")
        Spacer(Modifier.height(8.dp))

        when (hourlyWeatherResult) {
            is Result.Error -> GenericErrorMessage()
            Result.Loading -> SectionProgressBar()
            is Result.Success -> HourlyWeatherChart(hourlyWeatherResult.data)
        }
    }
}

@Composable
fun HourlyWeatherChart(hourlyWeather: List<HourWeather>) {
    val curveColor = MaterialTheme.colors.primary
    val curveBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.2f)
    val onBackgroundColor = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
    val cellSizeDp = 72.dp
    val cellSize = with(LocalDensity.current) { cellSizeDp.toPx() }

    val scrollState = rememberScrollState()
    val chartHeight = 320.dp
    val chartVerticalPadding = 56.dp
    val minTemp = hourlyWeather.minOf { it.facts.temperature }
    val maxTemp = hourlyWeather.maxOf { it.facts.temperature }
    val temperatureHeightStep =
        (chartHeight - chartVerticalPadding * 2) / (maxTemp - minTemp)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(chartHeight)
            .horizontalScroll(scrollState)
    ) {
        val canvasWidth = cellSizeDp * (hourlyWeather.size + 1)
        Canvas(
            modifier = Modifier
                .width(canvasWidth),
            onDraw = {
                val points = hourlyWeather.mapIndexed { index, item ->
                    Point(
                        (index.toFloat() + 1) * cellSize,
                        (maxTemp - item.facts.temperature.toFloat()) * temperatureHeightStep.toPx() + chartVerticalPadding.toPx()
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

                val curvePath = Path()
                val chartBottom = (chartHeight - chartVerticalPadding).toPx()
                val curveBackgroundPath = Path()
                val curveBackgroundOffset = 80f
                if (points.isNotEmpty() && connectionPoints1.isNotEmpty() && connectionPoints2.isNotEmpty()) {
                    curvePath.reset()
                    curveBackgroundPath.reset()
                    curvePath.moveTo(points.first().x, points.first().y)
                    curveBackgroundPath.moveTo(
                        points.first().x,
                        chartBottom + curveBackgroundOffset
                    )
                    curveBackgroundPath.lineTo(points.first().x, points.first().y)
                    val dashIntervals = floatArrayOf(10f, 20f)
                    val dashPhase = 25f
                    val dashVerticalPadding = 20f

                    for (i in 1 until points.size) {
                        curvePath.cubicTo(
                            connectionPoints1[i - 1].x,
                            connectionPoints1[i - 1].y,
                            connectionPoints2[i - 1].x,
                            connectionPoints2[i - 1].y,
                            points[i].x,
                            points[i].y
                        )
                        curveBackgroundPath.cubicTo(
                            connectionPoints1[i - 1].x,
                            connectionPoints1[i - 1].y,
                            connectionPoints2[i - 1].x,
                            connectionPoints2[i - 1].y,
                            points[i].x,
                            points[i].y
                        )

                        drawLine(
                            color = onBackgroundColor,
                            pathEffect = PathEffect.dashPathEffect(
                                dashIntervals,
                                dashPhase
                            ),
                            start = Offset(
                                points[i].x,
                                points[i].y + dashVerticalPadding
                            ),
                            end = Offset(
                                points[i].x,
                                chartBottom + curveBackgroundOffset - dashVerticalPadding
                            )
                        )

                        if (i == points.size - 1) {
                            curveBackgroundPath.lineTo(
                                points[i].x,
                                chartBottom + curveBackgroundOffset
                            )
                            curveBackgroundPath.lineTo(
                                points.first().x,
                                chartBottom + curveBackgroundOffset
                            )
                        }

                        val hourWeather = hourlyWeather.getOrNull(i - 1)

                        if (hourWeather != null) {
                            val paint = Paint()
                            paint.textAlign = Paint.Align.CENTER
                            paint.textSize = 54f
                            paint.color = onBackgroundColor.toArgb()
                            drawIntoCanvas { canvas ->
                                canvas.nativeCanvas.drawText(
                                    hourWeather.facts.temperature.toString() + "°",
                                    points[i].x,
                                    points[i].y - 50f,
                                    paint
                                )
                                canvas.nativeCanvas.drawText(
                                    "%02d".format(hourWeather.hour),
                                    points[i].x,
                                    chartBottom + chartVerticalPadding.toPx(),
                                    paint
                                )
                            }
                        }
                    }

                    drawPath(curvePath, curveColor, style = Stroke(width = 4.dp.toPx()))
                    drawPath(curveBackgroundPath, curveBackgroundColor, style = Fill)
                }
            }
        )
    }
}

data class Point(
    val x: Float,
    val y: Float,
)
