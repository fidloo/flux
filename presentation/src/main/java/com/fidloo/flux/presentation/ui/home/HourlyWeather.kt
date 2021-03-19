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
    val curveColor = MaterialTheme.colors.primary
    val curveBackgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.2f)
    val onBackgroundColor = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
    val cellSizeDp = 72.dp
    val cellSize = with(LocalDensity.current) { cellSizeDp.toPx() }

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
            is Result.Success -> {
                val hourlyWeather = hourlyWeatherResult.data
                val scrollState = rememberScrollState()
                val chartHeight = 300.dp
                val chartVerticalPadding = 36.dp
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
                    val canvasWidth = cellSizeDp * hourlyWeather.size
                    Canvas(modifier = Modifier
                        .width(canvasWidth),
                        onDraw = {
                            val points = hourlyWeather.map { item ->
                                Point(
                                    item.hour.toFloat() * cellSize,
                                    (maxTemp - item.facts.temperature.toFloat()) * temperatureHeightStep.toPx() + chartVerticalPadding.toPx()
                                )
                            }

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
                            if (points.isNotEmpty() && connectionPoints1.isNotEmpty() && connectionPoints2.isNotEmpty()) {
                                curvePath.reset()
                                curveBackgroundPath.reset()
                                curvePath.moveTo(points.first().x, points.first().y)
                                curveBackgroundPath.moveTo(
                                    points.first().x,
                                    chartBottom + chartVerticalPadding.toPx()
                                )
                                curveBackgroundPath.lineTo(points.first().x, points.first().y)

                                for (i in 1 until points.size) {
                                    val hourWeather = hourlyWeather[i]
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
                                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 20f), 25f),
                                        start = Offset(points[i].x, points[i].y + 20f),
                                        end = Offset(
                                            points[i].x,
                                            chartBottom + chartVerticalPadding.toPx() - 20f
                                        )
                                    )

                                    if (i == points.size - 1) {
                                        curveBackgroundPath.lineTo(
                                            points[i].x,
                                            chartBottom + chartVerticalPadding.toPx()
                                        )
                                        curveBackgroundPath.lineTo(
                                            points.first().x,
                                            chartBottom + chartVerticalPadding.toPx()
                                        )
                                    }

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

                                drawPath(curvePath, curveColor, style = Stroke(width = 4.dp.toPx()))
                                drawPath(curveBackgroundPath, curveBackgroundColor, style = Fill)
                            }
                        }
                    )
                }
            }
        }
    }
}

data class Point(
    val x: Float,
    val y: Float,
)
