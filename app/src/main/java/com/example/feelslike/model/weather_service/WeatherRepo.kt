package com.example.feelslike.model.weather_service

import com.example.feelslike.R
import com.example.feelslike.model.weather_service.WeatherApiService

class WeatherRepo(private val weatherInterface : WeatherApiService)
{
    val appId = R.string.WEATHER_API_KEY
    suspend fun searchByPlace(placeName : String, appId : String) =
        weatherInterface.searchWeatherByPlaceName(placeName, appId)
}