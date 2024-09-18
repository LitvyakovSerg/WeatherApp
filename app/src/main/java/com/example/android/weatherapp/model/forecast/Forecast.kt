package com.example.android.weatherapp.model.forecast

import com.example.android.weatherapp.model.Clouds
import com.example.android.weatherapp.model.Main
import com.example.android.weatherapp.model.Weather
import com.example.android.weatherapp.model.Wind

data class Forecast (
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: SysForecast,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)