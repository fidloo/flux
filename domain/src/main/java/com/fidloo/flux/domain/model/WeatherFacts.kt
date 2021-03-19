package com.fidloo.flux.domain.model

data class WeatherFacts(
    val temperature: Int,
    val apparentTemperature: Int,
    val precipitation: Float,
    val humidity: Float,
    val windSpeed: Float,
    val cloudCover: Float,
    val pressure: Float,
    val visibility: Float,
    val uvIndex: Int,
    val dewPoint: Int,
    val sunrise: String,
    val sunset: String,
)