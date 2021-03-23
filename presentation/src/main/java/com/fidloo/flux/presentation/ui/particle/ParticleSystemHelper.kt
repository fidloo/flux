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
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class ParticleSystemHelper(
    private val parameters: PrecipitationsParameters,
    private val frameWidth: Int,
    private val frameHeight: Int
) {

    private val _particles = mutableListOf<Particle>()
    val particles: List<Particle> = _particles

    fun generateParticles() {
        while (_particles.size < parameters.particleCount) {
            _particles.add(createParticle())
        }
    }

    private fun createParticle(): Particle {
        val randomWidth = getRandomWidth()
        val randomHeight = getRandomHeight(randomWidth)
        val angle = computeAngle()

        return Particle(
            x = generateX(),
            y = generateY(),
            width = randomWidth,
            height = randomHeight,
            speed = Random.nextFloat() * (parameters.maxSpeed - parameters.minSpeed) + parameters.minSpeed,
            angle = angle
        )
    }

    private fun generateX(startFromSourceEdge: Boolean = false): Float {
        val randomX = Random.nextInt(frameWidth).toFloat()
        return when (parameters.sourceEdge) {
            PrecipitationSourceEdge.TOP -> randomX
            PrecipitationSourceEdge.RIGHT -> if (startFromSourceEdge) frameWidth.toFloat() else randomX
            PrecipitationSourceEdge.BOTTOM -> randomX
            PrecipitationSourceEdge.LEFT -> if (startFromSourceEdge) 0f else randomX
        }
    }

    private fun generateY(startFromSourceEdge: Boolean = false): Float {
        val randomY = Random.nextInt(frameHeight).toFloat()
        return when (parameters.sourceEdge) {
            PrecipitationSourceEdge.TOP -> if (startFromSourceEdge) 0f else randomY
            PrecipitationSourceEdge.RIGHT -> randomY
            PrecipitationSourceEdge.BOTTOM -> if (startFromSourceEdge) frameHeight.toFloat() else randomY
            PrecipitationSourceEdge.LEFT -> randomY
        }
    }

    private fun isOutOfFrame(particle: Particle): Boolean {
        return when (parameters.sourceEdge) {
            PrecipitationSourceEdge.TOP -> {
                particle.y > frameHeight || particle.x < 0 || particle.x > frameWidth
            }
            PrecipitationSourceEdge.RIGHT -> {
                val result = particle.y - particle.height > frameHeight ||
                    particle.y + particle.height < 0 ||
                    particle.x + particle.width < 0

                if (result == true) {
                    Log.d("isOutOfFrame", "$result $particle $frameWidth $frameHeight")
                }
                result
            }
            PrecipitationSourceEdge.BOTTOM -> {
                particle.y < 0 || particle.x < 0 || particle.x > frameWidth
            }
            PrecipitationSourceEdge.LEFT -> {
                particle.y - particle.height > frameHeight ||
                    particle.y + particle.height < 0 ||
                    particle.x - particle.width > frameWidth
            }
        }
    }

    fun updateParticles(iteration: Long) {
        particles.forEach { particle ->
            if (isOutOfFrame(particle)) {
                val randomWidth = getRandomWidth()
                val randomHeight = getRandomHeight(randomWidth)
                val angle = computeAngle()

                particle.n = iteration
                particle.width = randomWidth
                particle.height = randomHeight
                particle.x = generateX(startFromSourceEdge = true)
                particle.y = generateY(startFromSourceEdge = true)
                particle.speed =
                    Random.nextFloat() * (parameters.maxSpeed - parameters.minSpeed) + parameters.minSpeed
                particle.angle = angle
            } else {
                particle.n = iteration
                particle.x = particle.x - (parameters.distancePerStep * particle.speed) * cos(
                    Math.toRadians(particle.angle.toDouble())
                ).toFloat()
                particle.y = particle.y - (parameters.distancePerStep * particle.speed) * sin(
                    Math.toRadians(particle.angle.toDouble())
                ).toFloat()
            }
        }
    }

    private fun getRandomWidth(): Float {
        return when (parameters.shape) {
            is PrecipitationShape.Circle -> Random.nextInt(
                parameters.shape.minRadius,
                parameters.shape.maxRadius
            ).toFloat()
            is PrecipitationShape.Line -> Random.nextInt(
                parameters.shape.minStrokeWidth,
                parameters.shape.maxStrokeWidth
            ).toFloat()
            is PrecipitationShape.Image -> Random.nextInt(
                parameters.shape.minWidth,
                parameters.shape.maxWidth
            ).toFloat()
        }
    }

    private fun getRandomHeight(width: Float): Float {
        return when (parameters.shape) {
            is PrecipitationShape.Circle -> width
            is PrecipitationShape.Line -> Random.nextInt(
                parameters.shape.minHeight,
                parameters.shape.maxHeight
            ).toFloat()
            is PrecipitationShape.Image -> Random.nextInt(
                parameters.shape.minHeight,
                parameters.shape.maxHeight
            ).toFloat()
        }
    }

    private fun computeAngle(): Int {
        return if (parameters.minAngle == parameters.maxAngle) {
            parameters.maxAngle
        } else {
            Random.nextInt(parameters.minAngle, parameters.maxAngle)
        }
    }
}
