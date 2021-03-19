package com.fidloo.flux.domain.repository

import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.model.CurrentWeather

interface WeatherRepository {
    suspend fun fetchCurrentWeather() : CurrentWeather
    suspend fun fetchHourlyWeather() : List<HourWeather>
    suspend fun fetchWeekWeather(): String
}