package com.example.android.weatherapp

import com.example.android.weatherapp.model.currentweather.WeatherResult
import com.example.android.weatherapp.model.forecast.City
import com.example.android.weatherapp.model.forecast.FiveDayForecast
import com.example.android.weatherapp.repository.WeatherRepository
import com.example.android.weatherapp.viewmodel.MainViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.`when`
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.weatherapp.model.Coord
import org.mockito.Mockito.mock

class MainViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val weatherRepositoryMock: WeatherRepository = mock()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(weatherRepositoryMock)
    }

    @Test
    fun `test getCoordinates`() = runBlocking {
        //Setup
        val city = "Testcity"
        val coordinates = Coord(1.0, 1.0)
        `when`(weatherRepositoryMock.getLocationCoordinates(city)).thenReturn(coordinates)

        //Call
        viewModel.getCoordinates(city)

        //Verification
        assertEquals(coordinates, viewModel.coordinatesResult.value)
    }

    @Test
    fun `test getCurrentWeather`() = runBlocking {
        //Setup
        val lat = 0.0
        val lon = 0.0
        val weatherResult = WeatherResult("main", "description", 1.0, 1, 1, 1.0)
        `when`(weatherRepositoryMock.getCurrentWeather(lat, lon)).thenReturn(weatherResult)

        //Call
        viewModel.getCurrentWeather(lat, lon)

        //Verification
        assertEquals(weatherResult, viewModel.currentWeatherResult.value)
    }

    @Test
    fun `test getForecast`() = runBlocking {
        //Setup
        val lat = 0.0
        val lon = 0.0
        val forecast = FiveDayForecast(City(Coord(1.0, 1.0), "Country", 1, "name", 100, 200, 300, 1), 1, "cod",
        emptyList(), 1
        )
        `when`(weatherRepositoryMock.getForecast(lat, lon)).thenReturn(forecast)

        //Call
        viewModel.getForecast(lat, lon)

        //Verification
        assertEquals(forecast, viewModel.forecastResult.value)
    }
}