package com.fidloo.flux.domain.model

import java.util.Date

data class DayWeather(
    val date: Date,
    val facts: WeatherFacts,
    val sunrise: String,
    val sunset: String,
)