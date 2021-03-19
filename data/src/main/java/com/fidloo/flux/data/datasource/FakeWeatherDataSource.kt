package com.fidloo.flux.data.datasource

import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.model.CurrentWeather
import com.fidloo.flux.domain.model.WeatherFacts
import java.util.Calendar

object FakeWeatherDataSource {

    fun getCurrentWeather(): CurrentWeather {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val hourFacts = next24HourWeather.first { it.hour == hour }.facts
        return CurrentWeather(
            hourWeather = hourFacts,
            sunrise = "06:46",
            sunset = "18h53",
            minTemperature = -1,
            maxTemperature = 9
        )
    }

    val next24HourWeather = listOf(
        HourWeather(
            0,
            WeatherFacts(
                temperature = 2,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            1,
            WeatherFacts(
                temperature = 1,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            2,
            WeatherFacts(
                temperature = 0,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            3,
            WeatherFacts(
                temperature = 0,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            4,
            WeatherFacts(
                temperature = -1,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            5,
            WeatherFacts(
                temperature = -1,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            6,
            WeatherFacts(
                temperature = 0,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            7,
            WeatherFacts(
                temperature = 1,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            8,
            WeatherFacts(
                temperature = 2,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            9,
            WeatherFacts(
                temperature = 3,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            10,
            WeatherFacts(
                temperature = 5,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            11,
            WeatherFacts(
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
            )
        ),
        HourWeather(
            12,
            WeatherFacts(
                temperature = 7,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            13,
            WeatherFacts(
                temperature = 8,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            14,
            WeatherFacts(
                temperature = 8,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            15,
            WeatherFacts(
                temperature = 8,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            16,
            WeatherFacts(
                temperature = 8,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            17,
            WeatherFacts(
                temperature = 9,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            18,
            WeatherFacts(
                temperature = 8,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            19,
            WeatherFacts(
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
            )
        ),
        HourWeather(
            20,
            WeatherFacts(
                temperature = 5,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            21,
            WeatherFacts(
                temperature = 4,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4
            )
        ),
        HourWeather(
            22,
            WeatherFacts(
                temperature = 3,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
        HourWeather(
            23,
            WeatherFacts(
                temperature = 2,
                apparentTemperature = 2,
                precipitation = 0.2f,
                humidity = 0.49f,
                windSpeed = 17f,
                cloudCover = 0.88f,
                pressure = 1.0f,
                visibility = 5f,
                uvIndex = 1,
                dewPoint = -4,
            )
        ),
    )
}