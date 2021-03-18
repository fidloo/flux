package com.fidloo.flux.domain.repository

interface WeatherRepository {
    suspend fun fetchCurrentWeather() : String
    suspend fun fetchHourlyWeather() : String
    suspend fun fetchWeekWeather(): String
}