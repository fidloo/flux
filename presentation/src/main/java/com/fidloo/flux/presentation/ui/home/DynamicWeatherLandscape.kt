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
import com.fidloo.flux.domain.base.Result
import com.fidloo.flux.domain.model.CurrentWeather
import com.fidloo.flux.presentation.R
import com.fidloo.flux.presentation.ui.component.CenteredProgressBar
import com.fidloo.flux.presentation.ui.component.GenericErrorMessage
import java.util.Calendar

@Composable
fun DynamicWeatherSection(
    currentWeather: Result<CurrentWeather>,
    time: Float,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
    ) {
        when (currentWeather) {
            is Result.Error -> GenericErrorMessage()
            Result.Loading -> CenteredProgressBar()
            is Result.Success -> DynamicWeatherLandscape(currentWeather.data, time, constraints)
        }
    }
}

@Composable
fun DynamicWeatherLandscape(weather: CurrentWeather, time: Float, constraints: Constraints) {
    var mountainDarkTintAlpha by rememberSaveable { mutableStateOf(0f) }
    var backgroundLayer2Alpha by rememberSaveable { mutableStateOf(0f) }
    val height = constraints.maxHeight
    val width = constraints.maxWidth

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val calendar = Calendar.getInstance()
        calendar.time = weather.time

//                val time = calendar[Calendar.HOUR_OF_DAY] * MINUTES_PER_HOUR +
//                    calendar[Calendar.MINUTE]
        val (sunriseHour, sunriseMinute) = weather.sunrise.split(":")
            .map { it.toFloat() }
        val sunriseAt = sunriseHour * 60 + sunriseMinute
        val (sunsetHour, sunsetMinute) = weather.sunset.split(":")
            .map { it.toFloat() }
        val sunsetAt = sunsetHour * 60 + sunsetMinute

        val sunProgress = (time - (sunriseAt - LANDSCAPE_TRANSITION_DURATION)) /
            ((sunsetAt + LANDSCAPE_TRANSITION_DURATION) - (sunriseAt - LANDSCAPE_TRANSITION_DURATION))

        val nightElapsedTimeInMin = when {
            time < sunriseAt -> time + MINUTES_PER_DAY - sunsetAt
            time > sunsetAt -> time - sunsetAt
            else -> 0f
        }
        val moonProgress = nightElapsedTimeInMin /
            (MINUTES_PER_DAY - sunsetAt + sunriseAt)

        val (backgroundLayer1Image, backgroundLayer2Image) = when {
            time < sunriseAt - LANDSCAPE_TRANSITION_DURATION -> R.drawable.night to null
            time < sunriseAt -> R.drawable.night to R.drawable.sunrise
            time < sunriseAt + LANDSCAPE_TRANSITION_DURATION -> R.drawable.sunrise to R.drawable.day
            time < sunsetAt - LANDSCAPE_TRANSITION_DURATION -> R.drawable.day to null
            time < sunsetAt -> R.drawable.day to R.drawable.sunset
            time < sunsetAt + LANDSCAPE_TRANSITION_DURATION -> R.drawable.sunset to R.drawable.night
            time > sunsetAt + LANDSCAPE_TRANSITION_DURATION -> R.drawable.night to null
            else -> null to null
        }

        val progress = when {
            time < sunriseAt - LANDSCAPE_TRANSITION_DURATION -> 0f
            time < sunriseAt -> (sunriseAt - time) / LANDSCAPE_TRANSITION_DURATION
            time < sunriseAt + LANDSCAPE_TRANSITION_DURATION -> {
                (sunriseAt + LANDSCAPE_TRANSITION_DURATION - time) / LANDSCAPE_TRANSITION_DURATION
            }
            time < sunsetAt - LANDSCAPE_TRANSITION_DURATION -> 0f
            time < sunsetAt -> (sunsetAt - time) / LANDSCAPE_TRANSITION_DURATION
            time < sunsetAt + LANDSCAPE_TRANSITION_DURATION -> {
                (sunsetAt + LANDSCAPE_TRANSITION_DURATION - time) / LANDSCAPE_TRANSITION_DURATION
            }
            time > sunsetAt + LANDSCAPE_TRANSITION_DURATION -> 0f
            else -> 0f
        }

        val mountainDarkTintPercent = when {
            time < sunriseAt - MOUNTAIN_TINT_TRANSITION_DURATION -> 1f
            time < sunriseAt + MOUNTAIN_TINT_TRANSITION_DURATION -> {
                1 - (time - sunriseAt + MOUNTAIN_TINT_TRANSITION_DURATION) / (2 * MOUNTAIN_TINT_TRANSITION_DURATION)
            }
            time < sunsetAt - MOUNTAIN_TINT_TRANSITION_DURATION -> 0f
            time < sunsetAt + MOUNTAIN_TINT_TRANSITION_DURATION -> {
                (time - sunsetAt + MOUNTAIN_TINT_TRANSITION_DURATION) / (2 * MOUNTAIN_TINT_TRANSITION_DURATION)
            }
            time > sunsetAt + MOUNTAIN_TINT_TRANSITION_DURATION -> 1f
            else -> 0f
        }
        mountainDarkTintAlpha = mountainDarkTintPercent * MOUNTAIN_TINT_ALPHA_MAX

        backgroundLayer2Alpha = 1 - progress
        val (backgroundLayer1, backgroundLayer2, mountain) = createRefs()

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
