package com.example.android.weatherapp.repository

import com.example.android.weatherapp.model.currentweather.WeatherResult
import com.example.android.weatherapp.model.forecast.FiveDayForecast

/**
 * Provides API connection with https: //openweathermap.org/
 */
interface WeatherRepository {

    /**
     * Getting location info like lat and lon
     */
    suspend fun getLocationCoordinates(city: String): com.example.android.weatherapp.model.Coord

    /**
     * Getting current weather for specific place by provide lat and lon
     */
    suspend fun getCurrentWeather(lat: Double, lon: Double) : WeatherResult

    /**
     * Getting forecast for specific place by provide lat and lon
     */
    suspend fun getForecast(lat: Double, lon: Double) : FiveDayForecast
}