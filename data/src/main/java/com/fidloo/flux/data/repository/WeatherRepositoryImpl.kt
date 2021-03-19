package com.fidloo.flux.data.repository

import com.fidloo.flux.data.datasource.FakeWeatherDataSource
import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.model.WeatherFacts
import com.fidloo.flux.domain.repository.WeatherRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(

) : WeatherRepository {
    override suspend fun fetchCurrentWeather(): WeatherFacts {
        // Simulate network delay
        delay(1L)
        return FakeWeatherDataSource.getCurrentWeather()
    }

    override suspend fun fetchHourlyWeather(): List<HourWeather> {
        // Simulate network delay
        delay(1L)
        return FakeWeatherDataSource.next24HourWeather
    }

    override suspend fun fetchWeekWeather(): String {
        // Simulate network delay
        delay(1500L)
        return "Hola"
    }
}