package com.example.android.weatherapp

import com.example.android.weatherapp.model.*
import com.example.android.weatherapp.model.currentweather.CurrentWeather
import com.example.android.weatherapp.model.forecast.*
import com.example.android.weatherapp.model.geocoding.Geocoding
import com.example.android.weatherapp.model.geocoding.GeocodingItem
import com.example.android.weatherapp.network.WeatherApi
import com.example.android.weatherapp.repository.WeatherRepositoryImpl
import com.example.android.weatherapp.repository.WeatherRepositoryImpl.Companion.APP_ID
import com.example.android.weatherapp.repository.WeatherRepositoryImpl.Companion.LIMIT
import com.example.android.weatherapp.repository.WeatherRepositoryImpl.Companion.METRIC
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
//import org.mockito.kotlin.mock
import retrofit2.Response
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class WeatherRepositoryImplTest {

    private val lat = 37.7749
    private val lon = -122.4194

    private val weatherApiMock: WeatherApi = mock()

    private lateinit var subject: WeatherRepositoryImpl

    @Before
    fun setUp() {
        subject = WeatherRepositoryImpl(weatherApiMock)
    }

    @Test
    fun getLocationCoordinates_success(): Unit = runBlocking {
        val city = "London"
        val expectedCoordinates = Coord(37.7749, -122.4194)
        val location = Geocoding()
        location.add(GeocodingItem("USA", 37.7749, null, -122.4194, "San Francisco", "state1"))
        location.add(GeocodingItem("USA", 40.7128, null, -74.0060, "New York", "state2"))
        val mockResponse = Response.success(location)

        `when`(weatherApiMock.getGeocoding(city, LIMIT, APP_ID)).thenReturn(mockResponse)

        //Call
        val result = subject.getLocationCoordinates(city)

        //Verification
        assertEquals(expectedCoordinates.lat, result.lat)
        assertEquals(expectedCoordinates.lon, result.lon)
    }

    @Test
    fun getCurrentWeather_success(): Unit = runBlocking {
        val location = CurrentWeather(
            "base",
            Clouds(1), 1, Coord(lat, lon), 1, 1,
            Main(1.0, 1, 1, 1, 1, 1.0, 1.0, 1.0), "name",
            Sys(
                "country", 1, 1, 1, 1
            ), 1, 1,
            listOf(Weather("description", "icon", 1, "main")), Wind(1, 1.0, 1.0)
        )
        val mockResponse = Response.success(location)

        `when`(weatherApiMock.getCurrentWeather(lat, lon, APP_ID, METRIC)).thenReturn(mockResponse)
        val result = subject.getCurrentWeather(lat, lon)

        assertEquals("main", result.main)
        assertEquals("description", result.description)
    }

    @Test
    fun getForecast_success() = runBlocking {
        val expectedCity = City(Coord(lat, lon), "San Francisco", 123, "US", 1620351986, 162, 202, 5)
        val expectedForecast = FiveDayForecast(expectedCity, 40, "cod", listOf(
            Forecast(
                Clouds(1), 1, "dt", Main(1.0, 1, 1, 1, 1, 1.1, 1.1, 1.1), 1.1,
                Rain(1.1), SysForecast("pod"), 1,
                listOf(Weather("description", "icon", 1, "Main")),
                Wind(1, 1.1, 1.1)
            )
        ), 0)

        val mockResponse = Response.success(expectedForecast)
        `when`(weatherApiMock.getForecast(lat, lon, APP_ID, METRIC)).thenReturn(mockResponse)

        val actualForecast = subject.getForecast(lat, lon)

        assertEquals(expectedForecast, actualForecast)
    }
}