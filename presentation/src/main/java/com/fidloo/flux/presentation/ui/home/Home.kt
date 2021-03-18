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
package com.fidloo.flux.presentation.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fidloo.flux.domain.base.Result
import com.fidloo.flux.domain.model.CurrentWeather
import com.fidloo.flux.presentation.ui.component.GenericErrorMessage
import com.fidloo.flux.presentation.ui.component.SectionHeader
import com.fidloo.flux.presentation.ui.component.SectionProgressBar
import com.fidloo.flux.presentation.ui.theme.BottomSheetShape
import com.fidloo.flux.presentation.ui.theme.FluxTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val viewState by viewModel.state.collectAsState()
    BackdropScaffold(
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
        frontLayerScrimColor = Color.Transparent,
        frontLayerElevation = FluxTheme.elevations.Backdrop,
        backLayerContent = {
            Column {
                Spacer(modifier = Modifier.height(56.dp))
                Text(text = "Hello")
                Spacer(modifier = Modifier.height(56.dp))
                Text(text = "Hello")
                Spacer(modifier = Modifier.height(56.dp))
                Text(text = "Hello")
                Spacer(modifier = Modifier.height(56.dp))
                Text(text = "Hello")
                Spacer(modifier = Modifier.height(56.dp))
                Text(text = "Hello")
            }
        },
        frontLayerContent = {
            DetailedWeather(viewState)
        },
        appBar = {}
    )
}

@Composable
fun DetailedWeather(viewState: HomeViewState) {
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
            CurrentWeather(viewState.currentWeather)
            SectionHeader(title = "Hourly weather", subtitle = "24-hour forecast")
            Spacer(Modifier.height(8.dp))

            when (viewState.hourlyWeather) {
                is Result.Error -> GenericErrorMessage()
                Result.Loading -> SectionProgressBar()
                is Result.Success -> {
                    Text(text = viewState.hourlyWeather.data)
                }
            }
            SectionHeader(title = "This week", subtitle = "7-day forecast")
            Spacer(Modifier.height(8.dp))
            when (viewState.weekWeather) {
                is Result.Error -> GenericErrorMessage()
                Result.Loading -> SectionProgressBar()
                is Result.Success -> {
                    Text(text = viewState.weekWeather.data)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CurrentWeather(currentWeatherResult: Result<CurrentWeather>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SectionHeader(title = "Details", subtitle = "Weather now")
        Spacer(Modifier.height(8.dp))

        when (currentWeatherResult) {
            is Result.Error -> GenericErrorMessage()
            Result.Loading -> SectionProgressBar()
            is Result.Success -> {
                val currentWeather = currentWeatherResult.data
                val weatherFacts = currentWeather.extractFacts()

                val itemsPerRow = 2
                weatherFacts.chunked(itemsPerRow).forEach { factsPerRow ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        factsPerRow.forEachIndexed { index, fact ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(1f / (itemsPerRow - index))
                            ) {
                                WeatherFact(fact)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherFact(item: WeatherFact) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = item.label,
                style = MaterialTheme.typography.h3,
            )
            Spacer(Modifier.height(4.dp))
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = item.value,
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    HomeScreen()
}
