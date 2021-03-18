package com.fidloo.flux.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fidloo.flux.domain.business.FetchCurrentWeather
import com.fidloo.flux.domain.business.FetchHourlyWeather
import com.fidloo.flux.domain.business.FetchWeekWeather
import com.fidloo.flux.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        viewModelScope.launch {
            combine(
                fetchCurrentWeather(Unit),
                fetchHourlyWeather(Unit),
                fetchWeekWeather(Unit),
            ) { currentWeather, hourlyWeather, weekWeather ->
                HomeViewState(
                    currentWeather = currentWeather,
                    hourlyWeather = hourlyWeather,
                    weekWeather = weekWeather
                )
            }.collect { _state.value = it }
        }
    }
}
