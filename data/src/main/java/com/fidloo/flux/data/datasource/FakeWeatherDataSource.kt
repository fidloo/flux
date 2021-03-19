package com.fidloo.flux.data.datasource

import com.fidloo.flux.domain.model.HourWeather
import com.fidloo.flux.domain.model.WeatherFacts
import java.util.Calendar

object FakeWeatherDataSource {

    fun getCurrentWeather(): WeatherFacts {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return next24HourWeather.first { it.hour == hour }.facts
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                dewPoint = -4,
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
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
                sunrise = "06:48",
                sunset = "18h50",
            )
        ),
    )
}