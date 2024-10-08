package com.example.android.weatherapp.di

import com.example.android.weatherapp.network.RetrofitHelper
import com.example.android.weatherapp.network.WeatherApi
import com.example.android.weatherapp.repository.WeatherRepository
import com.example.android.weatherapp.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideWeatherRepository(weatherApi: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(weatherApi)
    }

    @Provides
    fun provideRequestApi(): WeatherApi {
        return RetrofitHelper.getInstance().create(WeatherApi::class.java)
    }
}