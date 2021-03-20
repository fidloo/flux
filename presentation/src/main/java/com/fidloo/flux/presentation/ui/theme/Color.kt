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
package com.fidloo.flux.presentation.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val gray800 = Color(0xCC333333)
val gray900 = Color(0xff333333)
val rust300 = Color(0xFFE1AFAF)
val rust600 = Color(0xFF886363)
val taupe100 = Color(0xfff0eae2)
val taupe800 = Color(0xff655454)
val white150 = Color(0x26FFFFFF)
val white800 = Color(0xCCFFFFFF)
val white850 = Color(0xD9FFFFFF)
val white = Color(0xFFFFFFFF)
val black = Color(0xFF000000)
val yellow = Color(0xFFFFCF44)
val yellow500 = Color(0xFFF5BD1C)
val purple = Color(0XFF4A22A8)
val darkBlue = Color(0XFF0e0f6d)
val blue = Color(0XFF1753ce)
val skyBlue = Color(0XFF6691cb)
val sand = Color(0XFFd7975a)
val snow = Color(0xFF7C706A)
val darkGrey = Color(0xFF444444)

@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}
