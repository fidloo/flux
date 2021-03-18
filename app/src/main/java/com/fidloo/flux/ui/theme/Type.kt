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
package com.fidloo.flux.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fidloo.flux.R

private val Roboto = FontFamily(
    Font(R.font.roboto_regular),
)

private val Lato = FontFamily(
    Font(R.font.lato_regular),
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_black, FontWeight.Black),
)

val typography = typographyFromDefaults(
    h1 = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Black,
        fontSize = 24.sp,
        letterSpacing = 1.15.sp
    ),
    h2 = TextStyle(
        fontFamily = Roboto,
        fontSize = 15.sp,
        letterSpacing = 1.15.sp,

        ),
    h3 = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 0.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = Lato,
        fontSize = 14.sp,
        letterSpacing = 0.15.sp,
    ),
    body1 = TextStyle(
        fontFamily = Lato,
        fontSize = 14.sp,
        letterSpacing = 0.sp,
    ),
    button = TextStyle(
        fontFamily = Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        letterSpacing = 1.15.sp,
    ),
    caption = TextStyle(
        fontFamily = Roboto,
        fontSize = 12.sp,
        letterSpacing = 1.15.sp,
    ),
)

fun typographyFromDefaults(
    h1: TextStyle?,
    h2: TextStyle?,
    h3: TextStyle?,
    subtitle1: TextStyle?,
    body1: TextStyle?,
    button: TextStyle?,
    caption: TextStyle?,
): Typography {
    val defaults = Typography()
    return Typography(
        h1 = defaults.h1.merge(h1),
        h2 = defaults.h2.merge(h2),
        h3 = defaults.h3.merge(h3),
        subtitle1 = defaults.subtitle1.merge(subtitle1),
        body1 = defaults.body1.merge(body1),
        button = defaults.button.merge(button),
        caption = defaults.caption.merge(caption),
    )
}
