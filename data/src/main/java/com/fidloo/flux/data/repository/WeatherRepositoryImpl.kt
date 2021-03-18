package com.fidloo.flux.data.repository

import com.fidloo.flux.domain.model.CurrentWeather
import com.fidloo.flux.domain.repository.WeatherRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(

) : WeatherRepository {
    override suspend fun fetchCurrentWeather(): CurrentWeather {
        // Simulate network delay
        delay(1L)
        return CurrentWeather(
            temperature = 6,
            apparentTemperature = 2,
            precipitation = 0.2f,
            humidity = 0.49f,
            windSpeed = 17f,
            cloudCover = 0.88f,
            pressure = 1.0f,
            visibility = 5f,
            uvIndex = 1,
            dewPoint = -4,
            sunrise = "06:48",
            sunset = "18h50",
        )
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