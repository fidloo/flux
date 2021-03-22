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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fidloo.flux.domain.base.Result
import com.fidloo.flux.domain.business.HourlyWeatherCurveParameters
import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.model.HourlyWeather
import com.fidloo.flux.presentation.ui.component.GenericErrorMessage
import com.fidloo.flux.presentation.ui.component.SectionHeader
import com.fidloo.flux.presentation.ui.component.SectionProgressBar
import com.fidloo.flux.presentation.ui.utils.getIconRes
import java.util.Calendar
import java.util.Date

@Composable
fun HourlyWeather(
    hourlyWeatherResult: Result<HourlyWeather>,
    selectedTime: Date,
    onWeatherTimeSelected: (Date) -> Unit
) {
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
            is Result.Success -> HourlyWeatherChart(
                hourlyWeatherResult.data,
                selectedTime,
                onWeatherTimeSelected
            )
        }
    }
}

@Composable
fun HourlyWeatherChart(
    hourlyWeather: HourlyWeather,
    selectedTime: Date,
    onWeatherTimeSelected: (Date) -> Unit
) {
    val curveColor = MaterialTheme.colors.primary
    val curveBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.2f)
    val onBackgroundColor = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)

    val weatherPerHour = hourlyWeather.weatherPerHour
    val dpPerCell = HourlyWeatherCurveParameters.cellSize
    val scrollState = rememberScrollState()
    val chartHeight = HourlyWeatherCurveParameters.heightInterval

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
    ) {
        Column {
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
                                    canvas.nativeCanvas.drawText(
                                        hourWeather.facts.temperature.toString() + "°",
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

            Row(
                modifier = Modifier.padding(start = dpPerCell.dp / 2, top = 4.dp)
            ) {
                weatherPerHour.forEach { item ->
                    HourWeatherChartItemDescription(
                        item = item,
                        selectedTime = selectedTime,
                        modifier = Modifier.width(dpPerCell.dp),
                        onWeatherTimeSelected = onWeatherTimeSelected
                    )
                }
            }
        }
    }
}

@Composable
fun HourWeatherChartItemDescription(
    item: HourWeather,
    selectedTime: Date,
    modifier: Modifier = Modifier,
    onWeatherTimeSelected: (Date) -> Unit
) {

    val calendar = Calendar.getInstance()
    calendar.time = selectedTime
    val selectedHour = calendar[Calendar.HOUR_OF_DAY]
    calendar.time = item.time
    val itemHour = calendar[Calendar.HOUR_OF_DAY]
    val isSelected = selectedHour == itemHour

    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.2f) else Color.Transparent,
        elevation = 0.dp,
        border = if (selectedHour == itemHour) {
            BorderStroke(2.dp, MaterialTheme.colors.primary)
        } else {
            null
        }
    ) {
        Column(
            modifier = modifier
                .clickable { onWeatherTimeSelected(item.time) }
                .padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(item.facts.state.getIconRes()),
                contentDescription = "Weather satellite images",
                modifier = Modifier.size(36.dp),
            )

            val calendar = Calendar.getInstance()
            calendar.time = item.time
            val currentHour = calendar[Calendar.HOUR_OF_DAY]
            Text(
                text = "%02d".format(currentHour),
                style = MaterialTheme.typography.h2
            )
        }
    }
}
