package com.fidloo.flux.presentation.ui.home

import com.fidloo.flux.domain.base.Result

data class HomeViewState(
    val currentWeather: Result<String> = Result.Loading,
    val hourlyWeather: Result<String> = Result.Loading,
    val weekWeather: Result<String> = Result.Loading,
)