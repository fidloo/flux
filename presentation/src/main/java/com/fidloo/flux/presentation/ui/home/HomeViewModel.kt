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
import com.fidloo.flux.domain.base.successOr
import com.fidloo.flux.domain.business.FetchHourlyWeather
import com.fidloo.flux.domain.business.FetchWeatherAtTime
import com.fidloo.flux.domain.business.FetchWeekWeather
import com.fidloo.flux.domain.model.CurrentWeather.Companion.getDefault
import com.fidloo.flux.presentation.ui.home.hourly.HourlyWeatherType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchWeatherAtTime: FetchWeatherAtTime,
    private val fetchHourlyWeather: FetchHourlyWeather,
    private val fetchWeekWeather: FetchWeekWeather,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> = _state

    private val _particleAnimationIteration = MutableStateFlow(0L)
    val particleAnimationIteration: StateFlow<Long> = _particleAnimationIteration

    private val selectedWeatherTime = MutableStateFlow(Date())
    private val selectedFilter = MutableStateFlow(HourlyWeatherType.Temperature)

    var oldSelectedWeatherTime: Date = Date()

    private var job: Job? = null

    init {
        loadData()
        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                _particleAnimationIteration.value++
                delay(1L)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadData(userAction: Boolean = false) {
        job = viewModelScope.launch {
            combine(
                selectedWeatherTime.flatMapLatest { fetchWeatherAtTime(it) },
                fetchHourlyWeather(Unit),
                fetchWeekWeather(Unit),
                selectedFilter
            ) { currentWeather, hourlyWeather, weekWeather, selectedFilter ->

                // Prevent the swipe refresh to replace a successful state by a progress bar
                val newCurrentWeather = currentWeather.successOr(getDefault())

                val newHourlyWeather = if (!userAction || hourlyWeather.isSuccessful()) {
                    hourlyWeather
                } else {
                    state.value.hourlyWeather
                }

                val newWeekWeather = if (!userAction || weekWeather.isSuccessful()) {
                    weekWeather
                } else {
                    state.value.weekWeather
                }

                state.value.copy(
                    currentWeather = newCurrentWeather,
                    hourlyWeather = newHourlyWeather,
                    weekWeather = newWeekWeather,
                    selectedFilter = selectedFilter,
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

    fun onWeatherDateSelected(time: Date) {
        selectedWeatherTime.value = time
    }

    fun onFilterSelected(filter: HourlyWeatherType) {
        selectedFilter.value = filter
    }
}
