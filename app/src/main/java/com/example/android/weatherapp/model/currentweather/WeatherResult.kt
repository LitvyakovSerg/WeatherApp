package com.example.android.weatherapp.model.currentweather

data class WeatherResult(
    val main: String,
    val description: String,
    val temp: Double,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double
)
