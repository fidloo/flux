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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.fidloo.flux.domain.model.Point
import kotlin.random.Random

@Composable
fun Thunder(particleAnimationIteration: Long, width: Int, height: Int) {
    val thunderPath by remember { mutableStateOf(ThunderPath()) }
    val thunderIteration = (particleAnimationIteration / THUNDER_PHASE).toInt()

    val centerX = width / 2f
    val centerY = height / 2f
    if (thunderPath.id != thunderIteration) {
        val newPoints = mutableListOf<Point>()
        thunderPath.id = thunderIteration
        val pointCount = Random.nextInt(THUNDER_POINT_COUNT_MIN, THUNDER_POINT_COUNT_MAX).toFloat()
        val startX = Random.nextInt(width).toFloat()
        val startY = Random.nextInt((centerY / 2).toInt()).toFloat()

        newPoints.add(Point(startX, startY))

        while (newPoints.size < pointCount - 2) {
            val offsetX = Random.nextInt(THUNDER_PROGRESS_X_MIN, THUNDER_PROGRESS_X_MAX).toFloat()
            val offsetY = Random.nextInt(THUNDER_PROGRESS_Y_MIN, THUNDER_PROGRESS_Y_MAX).toFloat()
            val lastPoint = newPoints.last()

            newPoints.add(Point(lastPoint.x + offsetX, lastPoint.y + offsetY))
        }

        newPoints.add(Point(centerX, height.toFloat()))
        thunderPath.points = newPoints
    }

    if (particleAnimationIteration % THUNDER_PHASE < THUNDER_DURATION) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                val path = Path()
                thunderPath.points.forEachIndexed { index, point ->
                    if (index == 0) {
                        path.moveTo(point.x, point.y)
                    } else {
                        path.lineTo(point.x, point.y)
                    }
                }
                drawPath(path, Color.White, style = Stroke(width = 2.dp.toPx()))
            }
        )
    }
}

data class ThunderPath(
    var id: Int = 0,
    var points: List<Point> = emptyList()
)

private const val THUNDER_POINT_COUNT_MIN = 4
private const val THUNDER_POINT_COUNT_MAX = 8
private const val THUNDER_PROGRESS_X_MIN = -200
private const val THUNDER_PROGRESS_X_MAX = 200
private const val THUNDER_PROGRESS_Y_MIN = 50
private const val THUNDER_PROGRESS_Y_MAX = 200
private const val THUNDER_PHASE = 4000
private const val THUNDER_DURATION = 200
