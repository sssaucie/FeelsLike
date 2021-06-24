package com.example.feelslike.model.weather_service

import com.example.feelslike.R

class WeatherRepo(private val weatherInterface : WeatherApiService)
{
    val appId = R.string.WEATHER_API_KEY
    suspend fun searchByPlace(placeName : String, unit : String, appId : String) =
        weatherInterface.searchWeatherByPlaceName(placeName, unit, appId)
}