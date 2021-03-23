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
package com.fidloo.flux.presentation.ui.particle

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import com.fidloo.flux.presentation.ui.home.HomeViewModel
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class Particle(
    var n: Long = 0,
    var x: Float,
    var y: Float,
    var width: Float,
    var height: Float,
    var speed: Float,
    var angle: Int,
)

@Composable
fun Precipitations(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    parameters: PrecipitationsParameters
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val particles by rememberSaveable { mutableStateOf(mutableListOf<Particle>()) }

        if (particles.size < parameters.particleCount) {
            for (i in 0..parameters.particleCount) {
                val randomWidth = when (parameters.shape) {
                    is PrecipitationShape.Circle -> Random.nextInt(
                        parameters.shape.minRadius,
                        parameters.shape.maxRadius
                    ).toFloat()
                    is PrecipitationShape.Line -> Random.nextInt(
                        parameters.shape.minStrokeWidth,
                        parameters.shape.maxStrokeWidth
                    ).toFloat()
                }

                val randomHeight = when (parameters.shape) {
                    is PrecipitationShape.Circle -> randomWidth
                    is PrecipitationShape.Line -> Random.nextInt(
                        parameters.shape.minHeight,
                        parameters.shape.maxHeight
                    ).toFloat()
                }

                val angle = if (parameters.minAngle == parameters.maxAngle) {
                    parameters.maxAngle
                } else {
                    Random.nextInt(parameters.minAngle, parameters.maxAngle)
                }

                particles.add(
                    Particle(
                        x = Random.nextInt(constraints.maxWidth).toFloat(),
                        y = Random.nextInt(constraints.maxHeight).toFloat(),
                        width = randomWidth,
                        height = randomHeight,
                        speed = Random.nextFloat() * (parameters.maxSpeed - parameters.minSpeed) + parameters.minSpeed,
                        angle = angle
                    )
                )
            }
        }
        val precipitationTimer by viewModel.precipitationTimer.collectAsState()

        particles.forEach { particle ->
            if (particle.y > constraints.maxHeight || particle.x < 0 || particle.x > constraints.maxWidth) {
                val randomWidth = when (parameters.shape) {
                    is PrecipitationShape.Circle -> Random.nextInt(
                        parameters.shape.minRadius,
                        parameters.shape.maxRadius
                    ).toFloat()
                    is PrecipitationShape.Line -> Random.nextInt(
                        parameters.shape.minStrokeWidth,
                        parameters.shape.maxStrokeWidth
                    ).toFloat()
                }

                val randomHeight = when (parameters.shape) {
                    is PrecipitationShape.Circle -> randomWidth
                    is PrecipitationShape.Line -> Random.nextInt(
                        parameters.shape.minHeight,
                        parameters.shape.maxHeight
                    ).toFloat()
                }
                val angle = if (parameters.minAngle == parameters.maxAngle) {
                    parameters.maxAngle
                } else {
                    Random.nextInt(parameters.minAngle, parameters.maxAngle)
                }
                particle.n = precipitationTimer
                particle.x = Random.nextInt(constraints.maxWidth).toFloat()
                particle.y = 0f
                particle.width = randomWidth
                particle.height = randomHeight
                particle.speed =
                    Random.nextFloat() * (parameters.maxSpeed - parameters.minSpeed) + parameters.minSpeed
                particle.angle = angle
            } else {
                particle.n = precipitationTimer
                particle.x = particle.x - (parameters.distancePerStep * particle.speed) * cos(
                    Math.toRadians(particle.angle.toDouble())
                ).toFloat()
                particle.y = particle.y - (parameters.distancePerStep * particle.speed) * sin(
                    Math.toRadians(particle.angle.toDouble())
                ).toFloat()
            }
        }

        Canvas(
            modifier = modifier,
            onDraw = {
                particles.forEach { particle ->
                    when (parameters.shape) {
                        is PrecipitationShape.Circle -> {
                            drawCircle(
                                color = parameters.color,
                                radius = particle.width,
                                center = Offset(particle.x, particle.y)
                            )
                        }
                        is PrecipitationShape.Line -> {
                            val endX = particle.x - particle.height * cos(
                                Math.toRadians(particle.angle.toDouble())
                            ).toFloat()
                            val endY = particle.y - particle.height * sin(
                                Math.toRadians(particle.angle.toDouble())
                            ).toFloat()
                            drawLine(
                                color = parameters.color,
                                pathEffect = PathEffect.cornerPathEffect(20f),
                                start = Offset(particle.x, particle.y),
                                end = Offset(endX, endY),
                                strokeWidth = particle.width
                            )
                        }
                    }
                }
            }
        )
    }
}

data class PrecipitationsParameters(
    val particleCount: Int,
    val distancePerStep: Int,
    val minSpeed: Float,
    val maxSpeed: Float,
    val minAngle: Int,
    val maxAngle: Int,
    val shape: PrecipitationShape,
    val color: Color,
)

sealed class PrecipitationShape {
    data class Circle(
        val minRadius: Int,
        val maxRadius: Int,
    ) : PrecipitationShape()

    data class Line(
        val minStrokeWidth: Int,
        val maxStrokeWidth: Int,
        val minHeight: Int,
        val maxHeight: Int,
    ) : PrecipitationShape()
}

val snowParameters = PrecipitationsParameters(
    particleCount = 200,
    distancePerStep = 5,
    minSpeed = 0.1f,
    maxSpeed = 1f,
    minAngle = 260,
    maxAngle = 280,
    shape = PrecipitationShape.Circle(
        minRadius = 1,
        maxRadius = 10,
    ),
    color = Color.White
)

val rainParameters = PrecipitationsParameters(
    particleCount = 600,
    distancePerStep = 30,
    minSpeed = 0.7f,
    maxSpeed = 1f,
    minAngle = 265,
    maxAngle = 275,
    shape = PrecipitationShape.Line(
        minStrokeWidth = 1,
        maxStrokeWidth = 3,
        minHeight = 10,
        maxHeight = 15,
    ),
    color = Color.Gray
)
