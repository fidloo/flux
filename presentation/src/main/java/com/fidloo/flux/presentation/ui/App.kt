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
package com.fidloo.flux.presentation.ui

import android.graphics.Color
import android.view.Window
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.fidloo.flux.presentation.ui.navigation.LocalBackDispatcher
import com.fidloo.flux.presentation.ui.navigation.NavGraph
import com.fidloo.flux.presentation.ui.theme.FluxTheme
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun App(backDispatcher: OnBackPressedDispatcher, window: Window) {
    CompositionLocalProvider(LocalBackDispatcher provides backDispatcher) {
        ProvideWindowInsets {
            BarsTheming(window)
            FluxTheme {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NavGraph()
                }
            }
        }
    }
}

@Composable
fun BarsTheming(window: Window) {
    window.statusBarColor = Color.TRANSPARENT
    window.navigationBarColor = Color.TRANSPARENT
}
