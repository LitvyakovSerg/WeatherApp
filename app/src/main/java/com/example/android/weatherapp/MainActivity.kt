package com.example.android.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.android.weatherapp.network.RetrofitHelper
import com.example.android.weatherapp.network.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var locationLabel : TextView
    private lateinit var currentWeatherLabel : TextView
    private lateinit var forecastLabel : TextView

    private val appId = "800fa65f7ec4e97ee35d4d8ab9f6bf81"

    private val retrofitClient = RetrofitHelper.getInstance().create(WeatherApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationLabel = findViewById(R.id.locationLabel)
        currentWeatherLabel = findViewById(R.id.currentWeatherLabel)
        forecastLabel = findViewById(R.id.forecastLabel)

        lifecycleScope.launch(Dispatchers.IO) {

            //Делаем первый запрос для получения координат
            val result = retrofitClient.getGeocoding("London", limit = "1", appId)

            //Извлекаем из результата наши координаты lat и lon
            val latResult = result.body()?.first()?.lat ?:0.0
            val lonResult = result.body()?.first()?.lon ?:0.0

            //Делаем второй запрос для получения погоды. И передаем наши координаты.
            val currentWeather = retrofitClient.getCurrentWeather(latResult, lonResult, appId, "metric")

            //Делаем третий запрос для получения прогноза. И передаем наши координаты.
            val forecast = retrofitClient.getForecast(latResult, lonResult, appId, "metric")

            Log.d("testingRetrofit", "geoCoding---> ${result.body()}")
            Log.d("testingRetrofit", "currentWeather---> ${currentWeather.body()}")
            Log.d("testingRetrofit", "forecast---> ${forecast.message()}")
            Log.d("testingRetrofit", "forecast---> ${forecast.isSuccessful}")
            Log.d("testingRetrofit", "forecast---> ${forecast.body()}")

            // UI updates
            withContext(Dispatchers.Main) {
                locationLabel.text = "location: $latResult $lonResult"
                currentWeatherLabel.text = currentWeather.body()?.weather?.first()?.main ?: ""
                forecastLabel.text = forecast.body()?.list?.first()?.weather?.first()?.description ?: ""

            }

        }
    }
}