package com.example.android.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.android.weatherapp.databinding.ActivityMainBinding
import com.example.android.weatherapp.network.RetrofitHelper
import com.example.android.weatherapp.network.WeatherApi
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private val appId = "800fa65f7ec4e97ee35d4d8ab9f6bf81"

    private lateinit var binding: ActivityMainBinding

    private val retrofitClient = RetrofitHelper.getInstance().create(WeatherApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager
        tabLayout = binding.tabLayout

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
//                locationLabel.text = "location: $latResult $lonResult"
//                currentWeatherLabel.text = currentWeather.body()?.weather?.first()?.main ?: ""
//                forecastLabel.text = forecast.body()?.list?.first()?.weather?.first()?.description ?: ""

            }

        }
            prepareViewPager()

    }

    private fun prepareViewPager() {
        val fragmentList = arrayListOf(
            WeatherFragment.newInstance(),
            ForecastFragment.newInstance()
        )

        val tabTitlesArray = arrayOf("Weather", "Forecast")

        viewPager.adapter = ViewPagerAdapter(this, fragmentList)

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = tabTitlesArray[position]

        }.attach()
    }
}