package com.fidloo.flux.domain.model

data class HourWeather(
    val hour: Int,
    val facts: WeatherFacts,
)