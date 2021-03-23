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
package com.fidloo.flux.presentation.ui.home.landscape

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fidloo.flux.presentation.R
import com.fidloo.flux.presentation.ui.particle.Particles
import com.fidloo.flux.presentation.ui.particle.PrecipitationShape
import com.fidloo.flux.presentation.ui.particle.PrecipitationSourceEdge
import com.fidloo.flux.presentation.ui.particle.PrecipitationsParameters

@Composable
fun Clouds(
    modifier: Modifier = Modifier,
    tint: ColorFilter,
    particleAnimationIteration: Long,
    cloudCount: Int
) {
    BoxWithConstraints(modifier = modifier) {
        val cloudsParameters = PrecipitationsParameters(
            particleCount = cloudCount,
            distancePerStep = 1,
            minSpeed = 0.2f,
            maxSpeed = 0.6f,
            minAngle = 0,
            maxAngle = 0,
            shape = PrecipitationShape.Image(
                image = ImageBitmap.imageResource(R.drawable.cloud),
                minWidth = with(LocalDensity.current) { 60.dp.toPx() }.toInt(),
                maxWidth = with(LocalDensity.current) { 120.dp.toPx() }.toInt(),
                minHeight = with(LocalDensity.current) { 30.dp.toPx() }.toInt(),
                maxHeight = with(LocalDensity.current) { 60.dp.toPx() }.toInt(),
                colorFilter = tint

            ),
            sourceEdge = PrecipitationSourceEdge.RIGHT
        )

        Particles(
            modifier = Modifier
                .fillMaxSize(),
            iteration = particleAnimationIteration,
            parameters = cloudsParameters
        )
    }
}

@Preview
@Composable
fun CloudsPreview() {
    Clouds(
        tint = ColorFilter.tint(
            Color.Black.copy(alpha = 0.2f),
            BlendMode.SrcAtop
        ),
        particleAnimationIteration = 1,
        cloudCount = 8
    )
}
