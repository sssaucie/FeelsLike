package com.example.feelslike.utilities

import com.example.feelslike.R
import com.example.feelslike.model.weather_service.WeatherInterface

class WeatherRepo(private val weatherInterface : WeatherInterface)
{
    val appId = R.string.WEATHER_API_KEY
    suspend fun searchByPlace(placeName : String, appId : String) =
        weatherInterface.searchWeatherByPlaceName(placeName, appId)
}