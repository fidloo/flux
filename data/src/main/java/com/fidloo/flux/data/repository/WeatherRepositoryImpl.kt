package com.fidloo.flux.data.repository

import com.fidloo.flux.domain.repository.WeatherRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(

) : WeatherRepository {
    override suspend fun fetchCurrentWeather(): String {
        // Simulate network delay
        delay(1000L)
        return "Salut"
    }

    override suspend fun fetchHourlyWeather(): String {
        // Simulate network delay
        delay(2000L)
        return "Hey"
    }

    override suspend fun fetchWeekWeather(): String {
        // Simulate network delay
        delay(1500L)
        return "Hola"
    }
}