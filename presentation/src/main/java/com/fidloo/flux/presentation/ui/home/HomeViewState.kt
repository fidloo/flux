package com.fidloo.flux.presentation.ui.home

import com.fidloo.flux.domain.base.Result
import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.model.WeatherFacts

data class HomeViewState(
    val currentWeather: Result<WeatherFacts> = Result.Loading,
    val hourlyWeather: Result<List<HourWeather>> = Result.Loading,
    val weekWeather: Result<String> = Result.Loading,
)
