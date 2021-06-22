package com.example.feelslike.model.network

import androidx.lifecycle.LiveData
import com.example.feelslike.model.weather_service.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedWeather : LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location : String,
        languageCode : String
    )
}