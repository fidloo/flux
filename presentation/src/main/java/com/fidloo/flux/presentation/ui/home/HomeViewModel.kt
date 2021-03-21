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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fidloo.flux.domain.business.FetchCurrentWeather
import com.fidloo.flux.domain.business.FetchHourlyWeather
import com.fidloo.flux.domain.business.FetchWeekWeather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchCurrentWeather: FetchCurrentWeather,
    private val fetchHourlyWeather: FetchHourlyWeather,
    private val fetchWeekWeather: FetchWeekWeather,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> = _state

    private var job: Job? = null

    init {
        loadData()
    }

    private fun loadData(userAction: Boolean = false) {
        job = viewModelScope.launch {
            combine(
                fetchCurrentWeather(Unit),
                fetchHourlyWeather(Unit),
                fetchWeekWeather(Unit),
            ) { currentWeather, hourlyWeather, weekWeather ->
                state.value.copy(
                    currentWeather = currentWeather,
                    hourlyWeather = hourlyWeather,
                    weekWeather = weekWeather,
                    refreshing = if (userAction) {
                        currentWeather.isLoading() || hourlyWeather.isLoading() || weekWeather.isLoading()
                    } else {
                        false
                    }
                )
            }.collect { _state.value = it }
        }
    }

    fun refresh() {
        job?.cancel()
        _state.value = state.value.copy(refreshing = true)
        loadData(userAction = true)
    }
}
