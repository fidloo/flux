package com.fidloo.flux.domain.repository

import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.model.WeatherFacts

interface WeatherRepository {
    suspend fun fetchCurrentWeather() : WeatherFacts
    suspend fun fetchHourlyWeather() : List<HourWeather>
    suspend fun fetchWeekWeather(): String
}