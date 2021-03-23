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

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.fidloo.flux.domain.model.CurrentWeather
import com.fidloo.flux.domain.model.WeatherState
import com.fidloo.flux.presentation.R
import com.fidloo.flux.presentation.ui.particle.Precipitations
import com.fidloo.flux.presentation.ui.particle.rainParameters
import com.fidloo.flux.presentation.ui.particle.snowParameters
import java.util.Calendar

@Composable
fun DynamicWeatherSection(
    currentWeather: CurrentWeather,
    viewModel: HomeViewModel
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
    ) {
        DynamicWeatherLandscape(currentWeather, constraints, viewModel)
    }
}

@Composable
fun DynamicWeatherLandscape(
    weather: CurrentWeather,
    constraints: Constraints,
    viewModel: HomeViewModel
) {
    var mountainDarkTintAlpha by rememberSaveable { mutableStateOf(0f) }
    var backgroundLayer2Alpha by rememberSaveable { mutableStateOf(0f) }
    val height = constraints.maxHeight
    val width = constraints.maxWidth

    val calendar = Calendar.getInstance()
    calendar.time = viewModel.oldSelectedWeatherTime

    val oldTimeInMin = calendar[Calendar.HOUR_OF_DAY] * MINUTES_PER_HOUR +
        calendar[Calendar.MINUTE]
    calendar.time = weather.time
    val newTimeInMin = calendar[Calendar.HOUR_OF_DAY] * MINUTES_PER_HOUR +
        calendar[Calendar.MINUTE]

    val currentState = remember {
        MutableTransitionState(AnimatedTimeJumpProgress.START)
            .apply { targetState = AnimatedTimeJumpProgress.END }
    }
    val transition = updateTransition(currentState)

    val timeInMin by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 900,
                easing = LinearOutSlowInEasing
            )
        }
    ) { progress ->
        if (progress == AnimatedTimeJumpProgress.START) {
            oldTimeInMin.toFloat()
        } else {
            newTimeInMin.toFloat()
        }
    }

    if (timeInMin == newTimeInMin.toFloat()) {
        viewModel.oldSelectedWeatherTime = weather.time
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (sunriseHour, sunriseMinute) = weather.sunrise.split(":")
            .map { it.toFloat() }
        val sunriseAt = sunriseHour * 60 + sunriseMinute
        val (sunsetHour, sunsetMinute) = weather.sunset.split(":")
            .map { it.toFloat() }
        val sunsetAt = sunsetHour * 60 + sunsetMinute

        val sunProgress = (timeInMin - (sunriseAt - LANDSCAPE_TRANSITION_DURATION)) /
            ((sunsetAt + LANDSCAPE_TRANSITION_DURATION) - (sunriseAt - LANDSCAPE_TRANSITION_DURATION))

        val nightElapsedTimeInMin = when {
            timeInMin < sunriseAt -> timeInMin + MINUTES_PER_DAY - sunsetAt
            timeInMin > sunsetAt -> timeInMin - sunsetAt
            else -> 0f
        }
        val moonProgress = nightElapsedTimeInMin /
            (MINUTES_PER_DAY - sunsetAt + sunriseAt)

        val (backgroundLayer1Image, backgroundLayer2Image) = when {
            timeInMin < sunriseAt - LANDSCAPE_TRANSITION_DURATION -> R.drawable.night to null
            timeInMin < sunriseAt -> R.drawable.night to R.drawable.sunrise
            timeInMin < sunriseAt + LANDSCAPE_TRANSITION_DURATION -> R.drawable.sunrise to R.drawable.day
            timeInMin < sunsetAt - LANDSCAPE_TRANSITION_DURATION -> R.drawable.day to null
            timeInMin < sunsetAt -> R.drawable.day to R.drawable.sunset
            timeInMin < sunsetAt + LANDSCAPE_TRANSITION_DURATION -> R.drawable.sunset to R.drawable.night
            timeInMin > sunsetAt + LANDSCAPE_TRANSITION_DURATION -> R.drawable.night to null
            else -> null to null
        }

        val progress = when {
            timeInMin < sunriseAt - LANDSCAPE_TRANSITION_DURATION -> 0f
            timeInMin < sunriseAt -> (sunriseAt - timeInMin) / LANDSCAPE_TRANSITION_DURATION
            timeInMin < sunriseAt + LANDSCAPE_TRANSITION_DURATION -> {
                (sunriseAt + LANDSCAPE_TRANSITION_DURATION - timeInMin) / LANDSCAPE_TRANSITION_DURATION
            }
            timeInMin < sunsetAt - LANDSCAPE_TRANSITION_DURATION -> 0f
            timeInMin < sunsetAt -> (sunsetAt - timeInMin) / LANDSCAPE_TRANSITION_DURATION
            timeInMin < sunsetAt + LANDSCAPE_TRANSITION_DURATION -> {
                (sunsetAt + LANDSCAPE_TRANSITION_DURATION - timeInMin) / LANDSCAPE_TRANSITION_DURATION
            }
            timeInMin > sunsetAt + LANDSCAPE_TRANSITION_DURATION -> 0f
            else -> 0f
        }

        val mountainDarkTintPercent = when {
            timeInMin < sunriseAt - MOUNTAIN_TINT_TRANSITION_DURATION -> 1f
            timeInMin < sunriseAt + MOUNTAIN_TINT_TRANSITION_DURATION -> {
                1 - (timeInMin - sunriseAt + MOUNTAIN_TINT_TRANSITION_DURATION) / (2 * MOUNTAIN_TINT_TRANSITION_DURATION)
            }
            timeInMin < sunsetAt - MOUNTAIN_TINT_TRANSITION_DURATION -> 0f
            timeInMin < sunsetAt + MOUNTAIN_TINT_TRANSITION_DURATION -> {
                (timeInMin - sunsetAt + MOUNTAIN_TINT_TRANSITION_DURATION) / (2 * MOUNTAIN_TINT_TRANSITION_DURATION)
            }
            timeInMin > sunsetAt + MOUNTAIN_TINT_TRANSITION_DURATION -> 1f
            else -> 0f
        }
        mountainDarkTintAlpha = mountainDarkTintPercent * MOUNTAIN_TINT_ALPHA_MAX

        backgroundLayer2Alpha = 1 - progress
        val (backgroundLayer1, backgroundLayer2, mountain, particles) = createRefs()

        if (backgroundLayer1Image != null) {
            Image(
                painter = painterResource(backgroundLayer1Image),
                contentDescription = stringResource(R.string.sky),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(backgroundLayer1) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
            )
        }
        if (backgroundLayer2Image != null) {
            Image(
                painter = painterResource(backgroundLayer2Image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(backgroundLayer2) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                alpha = backgroundLayer2Alpha
            )
        }

        val imageSize = with(LocalDensity.current) { 64.dp.toPx() }
        val x2 = width - imageSize
        val a = -1 * (height + imageSize) / (
            ((width / 2) - width - imageSize) *
                (width / 2)
            )

        val sunX = (width - imageSize) * (1 - sunProgress.coerceIn(0f, 1f))
        val sunY = a * (sunX - x2) * sunX + height - imageSize - SUN_BOTTOM_MARGIN

        val moonX = (width - imageSize) * (1 - moonProgress)
        val moonY = a * (moonX - x2) * moonX + height - imageSize - MOON_BOTTOM_MARGIN

        Image(
            painter = painterResource(R.drawable.sun),
            contentDescription = stringResource(R.string.sun),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(
                    x = with(LocalDensity.current) { sunX.toDp() },
                    y = with(LocalDensity.current) { sunY.toDp() }
                )
                .size(64.dp),
        )
        Image(
            painter = painterResource(R.drawable.moon),
            contentDescription = stringResource(R.string.moon),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .offset(
                    x = with(LocalDensity.current) { moonX.toDp() },
                    y = with(LocalDensity.current) { moonY.toDp() }
                )
                .size(64.dp),
        )
        Image(
            painter = painterResource(R.drawable.landscape),
            contentDescription = stringResource(R.string.moutain),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(mountain) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            colorFilter = ColorFilter.tint(
                Color.Black.copy(alpha = mountainDarkTintAlpha),
                BlendMode.SrcAtop
            )
        )

        val weatherState = weather.hourWeather.state
        Crossfade(targetState = weatherState) { state ->
            val precipitationsParameters = when (state) {
                WeatherState.RAIN -> rainParameters
                WeatherState.HEAVY_RAIN -> rainParameters.copy(particleCount = 2000)
                WeatherState.THUNDERSTORM -> rainParameters.copy(
                    particleCount = 2000,
                    minAngle = 260,
                    maxAngle = 280,
                )
                WeatherState.SNOW -> snowParameters
                else -> null
            }
            if (precipitationsParameters != null) {
                Precipitations(
                    modifier = Modifier
                        .fillMaxSize()
                        .constrainAs(particles) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        },
                    viewModel = viewModel,
                    parameters = precipitationsParameters
                )
            }
        }
    }
}

private const val MINUTES_PER_HOUR = 60
private const val HOURS_PER_DAY = 24
private const val MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY
private const val LANDSCAPE_TRANSITION_DURATION = 45
private const val MOUNTAIN_TINT_TRANSITION_DURATION = 80
private const val SUN_BOTTOM_MARGIN = 60
private const val MOON_BOTTOM_MARGIN = 60
private const val MOUNTAIN_TINT_ALPHA_MAX = 0.4f

private enum class AnimatedTimeJumpProgress { START, END }
