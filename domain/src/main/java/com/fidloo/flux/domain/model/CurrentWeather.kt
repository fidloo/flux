package com.fidloo.flux.domain.model

import java.util.Date

data class CurrentWeather(
    val hourWeather: WeatherFacts,
    val sunrise: String,
    val sunset: String,
    val minTemperature: Int,
    val maxTemperature: Int,
)