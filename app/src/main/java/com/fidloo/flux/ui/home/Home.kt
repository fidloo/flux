/*
 * Copyright 2020 The Android Open Source Project
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
package com.fidloo.flux.ui.home

import android.widget.Space
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fidloo.flux.ui.component.SectionHeader
import com.fidloo.flux.ui.theme.BottomSheetShape
import com.fidloo.flux.ui.theme.FluxTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Home() {
    BackdropScaffold(
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
        frontLayerScrimColor = Color.Transparent,
        frontLayerElevation = FluxTheme.elevations.Backdrop,
        backLayerContent = {
            Column {
                Spacer(modifier = Modifier.height(56.dp))
                Text(
                    text = "Hello",
                )
                Spacer(modifier = Modifier.height(56.dp))
                Text(
                    text = "Hello",
                )
                Spacer(modifier = Modifier.height(56.dp))
                Text(
                    text = "Hello",
                )
                Spacer(modifier = Modifier.height(56.dp))
                Text(
                    text = "Hello",
                )
                Spacer(modifier = Modifier.height(56.dp))
                Text(
                    text = "Hello",
                )
            }
        },
        frontLayerContent = {
            DetailedWeather()
        },
        appBar = {}
    )
}

@Composable
fun DetailedWeather() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
        shape = BottomSheetShape
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
                .verticalScroll(scrollState)
        ) {
            SectionHeader(title = "Details", subtitle = "Weather now")
            Spacer(Modifier.height(8.dp))
            for (i in 1..5) {
                Column(Modifier.fillMaxWidth()) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = "Item $i",
                            modifier = Modifier.padding(vertical = 20.dp),
                            style = MaterialTheme.typography.body1,
                        )
                    }
                    Divider(color = Color.LightGray)
                }
            }
            SectionHeader(title = "Hourly weather", subtitle = "24-hour forecast")
            Spacer(Modifier.height(8.dp))
            for (i in 1..5) {
                Column(Modifier.fillMaxWidth()) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = "Item $i",
                            modifier = Modifier.padding(vertical = 20.dp),
                            style = MaterialTheme.typography.body1,
                        )
                    }
                    Divider(color = Color.LightGray)
                }
            }
            SectionHeader(title = "This week", subtitle = "7-day forecast")
            Spacer(Modifier.height(8.dp))
            for (i in 1..5) {
                Column(Modifier.fillMaxWidth()) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = "Item $i",
                            modifier = Modifier.padding(vertical = 20.dp),
                            style = MaterialTheme.typography.body1,
                        )
                    }
                    Divider(color = Color.LightGray)
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    Home()
}