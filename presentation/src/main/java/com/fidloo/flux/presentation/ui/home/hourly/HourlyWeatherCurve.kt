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
package com.fidloo.flux.presentation.ui.home.hourly

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fidloo.flux.domain.business.HourlyWeatherCurveParameters
import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.model.HourlyWeather
import com.fidloo.flux.domain.model.HourlyWeatherCurvePoints
import com.fidloo.flux.domain.model.HourlyWeatherType
import com.fidloo.flux.domain.model.WeatherFacts
import java.util.Calendar

@Composable
fun HourlyWeatherCurve(
    hourlyWeather: HourlyWeather,
    type: HourlyWeatherType
) {
    val curveColor = MaterialTheme.colors.primary
    val curveBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.2f)
    val onBackgroundColor = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
    val chartHeight = HourlyWeatherCurveParameters.heightInterval
    val weatherPerHour = hourlyWeather.weatherPerHour
    val dpPerCell = HourlyWeatherCurveParameters.cellSize
    val canvasWidth = dpPerCell.dp * (weatherPerHour.size + 1)
    Canvas(
        modifier = Modifier
            .height(chartHeight.dp)
            .width(canvasWidth),
        onDraw = {

            val points = hourlyWeather.hourlyWeatherCurvePoints.points.map {
                it.copy(
                    x = it.x.dp.toPx(),
                    y = it.y.dp.toPx()
                )
            }
            val connectionPoints1 =
                hourlyWeather.hourlyWeatherCurvePoints.connectionPoints1.map {
                    it.copy(
                        x = it.x.dp.toPx(),
                        y = it.y.dp.toPx()
                    )
                }
            val connectionPoints2 =
                hourlyWeather.hourlyWeatherCurvePoints.connectionPoints2.map {
                    it.copy(
                        x = it.x.dp.toPx(),
                        y = it.y.dp.toPx()
                    )
                }

            val curvePath = Path()
            val curveBackgroundPath = Path()
            if (points.isNotEmpty() && connectionPoints1.isNotEmpty() && connectionPoints2.isNotEmpty()) {
                curvePath.reset()
                curveBackgroundPath.reset()
                curvePath.moveTo(points.first().x, points.first().y)
                curveBackgroundPath.moveTo(
                    points.first().x,
                    chartHeight.dp.toPx()
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
                            chartHeight.dp.toPx() - dashVerticalPadding
                        )
                    )

                    if (i == points.size - 1) {
                        curveBackgroundPath.lineTo(
                            points[i].x,
                            chartHeight.dp.toPx()
                        )
                        curveBackgroundPath.lineTo(
                            points.first().x,
                            chartHeight.dp.toPx()
                        )
                    }

                    val hourWeather = weatherPerHour.getOrNull(i - 1)

                    if (hourWeather != null) {
                        val paint = Paint()
                        paint.textAlign = Paint.Align.CENTER
                        paint.textSize = 54f
                        paint.color = onBackgroundColor.toArgb()
                        drawIntoCanvas { canvas ->
                            val text = when (type) {
                                HourlyWeatherType.Temperature -> {
                                    hourWeather.facts.temperature.toInt().toString() + "°"
                                }
                                HourlyWeatherType.Wind -> {
                                    hourWeather.facts.windSpeed.toInt().toString()
                                }
                                HourlyWeatherType.CloudCover -> {
                                    (hourWeather.facts.cloudCover * 100).toInt().toString() + "%"
                                }
                            }

                            canvas.nativeCanvas.drawText(
                                text,
                                points[i].x,
                                points[i].y - 50f,
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

@Preview
@Composable
fun HourlyWeatherCurvePreview() {
    val calendar = Calendar.getInstance()
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0

    val weatherPerHour = (1..10).map { index ->
        if (index != 0) {
            calendar.add(Calendar.HOUR, 1)
        }

        HourWeather(
            time = calendar.time,
            facts = WeatherFacts.Default.copy(temperature = index.toFloat()),
            night = false
        )
    }
    val item = HourlyWeather(
        weatherPerHour = weatherPerHour,
        hourlyWeatherCurvePoints = HourlyWeatherCurvePoints()
    )
    HourlyWeatherCurve(item, HourlyWeatherType.Temperature)
}
