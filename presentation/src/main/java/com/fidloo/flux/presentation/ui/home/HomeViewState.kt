package com.fidloo.flux.presentation.ui.home

import com.fidloo.flux.domain.base.Result
import com.fidloo.flux.domain.model.CurrentWeather

data class HomeViewState(
    val currentWeather: Result<CurrentWeather> = Result.Loading,
    val hourlyWeather: Result<String> = Result.Loading,
    val weekWeather: Result<String> = Result.Loading,
)
